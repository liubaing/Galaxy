package com.liubaing.galaxy.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * 会话状态
 *
 * @author heshuai
 * @version 15-1-21.
 */
@Repository
public class SessionRepository {

    @Autowired
    private final StringRedisTemplate redisTemplate;

    public SessionRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

}