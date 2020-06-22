package com.xxmlp.service;

import com.xxmlp.po.Session;
import org.springframework.stereotype.Service;

@Service
public class SessionRedisServiceImpl extends RedisService<Session>{
    //自定义redis key作为Hash表的key名称
    private static final String REDIS_KEY = "USER_KEY";

    @Override
    protected String getRedisKey() {
        // TODO Auto-generated method stub
        return REDIS_KEY;
    }
}
