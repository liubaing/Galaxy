package com.liubaing.galaxy.repository;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 会话状态
 *
 * @author heshuai
 * @version 15-1-21.
 */
public class SessionRepository {

    private final StringRedisTemplate redisTemplate;

    public SessionRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

}