package com.xxmlp.service;

import com.xxmlp.po.Session;

public interface SessionService {
    Session getSession(Long id);
    Session saveSession(Session session);
    Session updateSession(Session session,Long id);
    Session getSessionById(Long id,String sessionId);
}
