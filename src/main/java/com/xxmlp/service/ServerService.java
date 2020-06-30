package com.xxmlp.service;

import com.xxmlp.po.Server;
import com.xxmlp.po.Server;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServerService {

    Server saveServer(Server server);

    Server getServer(Long id);

    Server getServerByIp(String ip);

    Page<Server> listAllServer(Pageable pageable);

    Server updateServer(Long id, Server server);

    void deleteServer(Long id);
}
