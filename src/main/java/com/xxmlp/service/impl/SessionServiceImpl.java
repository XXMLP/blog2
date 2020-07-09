package com.xxmlp.service.impl;

import com.xxmlp.NotFoundException;
import com.xxmlp.dao.SessionRepository;
import com.xxmlp.po.Session;
import com.xxmlp.service.redis.SessionRedisServiceImpl;
import com.xxmlp.service.SessionService;
import com.xxmlp.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Resource
    private SessionRedisServiceImpl sessionRedisService;

    @Transactional
    @Cacheable(value="sessionCache") //缓存,这里没有指定key.
    @Override
    public Session getSession(Long id){
        return sessionRepository.findByUserId(id);
    }
    
    @Transactional
    //allEntries 清空缓存所有属性 确保更新后缓存刷新
    @CacheEvict(value="sessionCache", allEntries=true)
    @Override
    public void saveSession(Session session){
        sessionRepository.save(session);
        if(session.getRedisKey()==null||"".equals(session.getRedisKey().trim())){
            session.setRedisKey(session.getUserId().toString());
            sessionRepository.save(session);
        }
        sessionRedisService.put(session.getRedisKey(), session, 86400);
    }
    
    @Transactional
    @Cacheable(value="sessionCache") //缓存,这里没有指定key.
    @Override
    public Session getSessionById(Long id,String sessionId){
        return sessionRepository.findBySessionIdAndUserId(sessionId,id);
    }

    //allEntries 清空缓存所有属性 确保更新后缓存刷新
    @Transactional
    @CacheEvict(value="sessionCache", allEntries=true)
    @Override
    public void delete(Long id) {
        Session session =sessionRepository.findByUserId(id);
        if (session != null) {
            sessionRedisService.remove(session.getRedisKey());
            sessionRepository.delete(session.getId());
        }

    }
}
