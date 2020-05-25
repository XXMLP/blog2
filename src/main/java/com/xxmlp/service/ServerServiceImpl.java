package com.xxmlp.service;

import com.xxmlp.NotFoundException;
import com.xxmlp.dao.ServerRepository;
import com.xxmlp.po.Server;
import com.xxmlp.po.Server;
import com.xxmlp.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ServerServiceImpl implements ServerService {

    @Autowired
    private ServerRepository serverRepository;


    @Transactional
    @Override
    public Server saveServer(Server server){
        return serverRepository.save(server);
    }

    @Transactional
    @Override
    public Server getServer(Long id) {
        return serverRepository.findOne(id);
    }

    @Override
    public Server getServerByIp(String ip) {
        return serverRepository.findByIp(ip);
    }

    @Transactional
    @Override
    public Page<Server> listAllServer(Pageable pageable) {
        return serverRepository.findAll(pageable);
    }

    @Override
    public List<Server> listServer() {
        return serverRepository.findAll();
    }

    @Transactional
    @Override
    public Server updateServer(Long id, Server server) {
        Server t = serverRepository.findOne(id);
        if (t == null) {
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(server,t, MyBeanUtils.getNullPropertyNames(server));
        return serverRepository.save(t);
    }



    @Transactional
    @Override
    public void deleteServer(Long id) {
        serverRepository.delete(id);
    }
}
