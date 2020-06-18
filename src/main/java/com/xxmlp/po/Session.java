package com.xxmlp.po;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_session")
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;
    private String redisKey;//redis中的key
    @Id
    private Long id;
    private String sessionId;


    public Session() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
