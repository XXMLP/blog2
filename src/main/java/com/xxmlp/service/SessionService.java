package com.xxmlp.service;

import com.xxmlp.po.Session;

public interface SessionService {

    Session getSession(Long id);

    void saveSession(Session session);

    void updateSession(Session session, Long id);

    Session getSessionById(Long id,String sessionId);

    void delete(Long id);
}
