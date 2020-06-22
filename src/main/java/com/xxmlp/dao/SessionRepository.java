package com.xxmlp.dao;

import com.xxmlp.po.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session,Long> {

    Session findBySessionIdAndUserId(String sessionId,Long id);

    Session findByUserId(Long id);
}
