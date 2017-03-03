package com.liubaing.galaxy.repository;

import com.liubaing.galaxy.cache.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

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

    public static void loadAllAccount(Map<String, String> accountMap) {
        RedisClient.getInstance().doRedisOps(jedis -> jedis.hmset(KeyUtils.account(), accountMap));
    }

}