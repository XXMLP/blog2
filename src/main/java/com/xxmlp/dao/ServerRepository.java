package com.xxmlp.dao;

import com.xxmlp.po.Server;
import com.xxmlp.po.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server,Long> {

    Server findByIp(String ip);
}
