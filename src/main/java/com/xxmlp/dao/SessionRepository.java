package com.xxmlp.dao;

import com.xxmlp.po.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface SessionRepository extends JpaRepository<Session,Long>, JpaSpecificationExecutor<Session> {

    Session findBySessionIdAndUserId(String sessionId,Long id);

    @Query("select s from Session s where s.userId = ?1")
    Session findByUserId(Long id);
}
