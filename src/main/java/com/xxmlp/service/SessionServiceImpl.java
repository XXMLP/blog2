package com.xxmlp.service;

import com.xxmlp.NotFoundException;
import com.xxmlp.dao.SessionRepository;
import com.xxmlp.po.Session;
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
        return sessionRepository.findOne(id);
    }
    
    @Transactional
    //allEntries 清空缓存所有属性 确保更新后缓存刷新
    @CacheEvict(value="sessionCache", allEntries=true)
    @Override
    public void saveSession(Session session){
        sessionRepository.save(session);
        if(session.getRedisKey()==null||"".equals(session.getRedisKey().trim())){
            session.setRedisKey(session.getId().toString());
            sessionRepository.save(session);
        }
        sessionRedisService.put(session.getRedisKey(), session, 86400);
    }
    
    @Transactional
    //allEntries 清空缓存所有属性 确保更新后缓存刷新
    @CacheEvict(value="sessionCache", allEntries=true)
    @Override
    public void updateSession(Session session, Long id){
        Session t = sessionRepository.findOne(id);
        if (t == null) {
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(session,t, MyBeanUtils.getNullPropertyNames(session));
        sessionRepository.save(session);
        if(session.getRedisKey()==null||"".equals(session.getRedisKey().trim())){
            session.setRedisKey(session.getId().toString());
            sessionRepository.save(session);
        }
        sessionRedisService.put(session.getRedisKey(), session, 86400);
    }

    @Transactional
    @Cacheable(value="sessionCache") //缓存,这里没有指定key.
    @Override
    public Session getSessionById(Long id,String sessionId){
        return sessionRepository.findBySessionIdAndId(sessionId,id);
    }

    //allEntries 清空缓存所有属性 确保更新后缓存刷新
//    @CacheEvict(value="sessionCache", allEntries=true)
//    @Override
//    public void delete(Integer id) {
//        // TODO Auto-generated method stub
//        Session session=sessionRepository.findOne(id);
//        sessionRedisService.remove(session.getRedisKey());
//        sessionRepository.delete(id);
//    }
}
