package com.xxmlp.service;

import com.xxmlp.NotFoundException;
import com.xxmlp.dao.SessionRepository;
import com.xxmlp.po.Session;
import com.xxmlp.po.Session;
import com.xxmlp.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Transactional
    @Override
    public Session getSession(Long id){
        return sessionRepository.findOne(id);
    }
    
    @Transactional
    @Override
    public Session saveSession(Session session){
        return sessionRepository.save(session);
    }
    
    @Transactional
    @Override
    public Session updateSession(Session session,Long id){
        Session t = sessionRepository.findOne(id);
        if (t == null) {
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(session,t, MyBeanUtils.getNullPropertyNames(session));
        return sessionRepository.save(t);
    }

    @Transactional
    @Override
    public Session getSessionById(Long id,String sessionId){
        return sessionRepository.findBySessionIdAndId(sessionId,id);
    }
    
    
}
