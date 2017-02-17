package com.liubaing.galaxy.cache;

import com.liubaing.galaxy.util.ConfigUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;
import java.util.function.Function;

/**
 * Redis客户端
 *
 * <p>使用方法：</p>
 * <code>RedisClient.getInstance().doRedisOps(jedis -> jedis.get(key));</code>
 *
 * @author heshuai
 * @version 2016/11/18.
 */
public class RedisClient {

    public static RedisClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private final static RedisClient INSTANCE = new RedisClient();
    }

    private RedisClient() {
        ConfigUtils.loadProperties(RedisConstants.COMMON_CONFIG);
        Properties properties = ConfigUtils.loadProperties("config-redis-pool.properties");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(NumberUtils.toInt(properties.getProperty("jedis.pool.max-total"), 512));
        jedisPoolConfig.setMaxIdle(NumberUtils.toInt(properties.getProperty("jedis.pool.max-idle"), 64));
        jedisPoolConfig.setTestOnBorrow(BooleanUtils.toBoolean(properties.getProperty("jedis.pool.test-on-borrow")));
        jedisPoolConfig.setMaxWaitMillis(NumberUtils.toInt(properties.getProperty("jedis.pool.max-wait-millis"), RedisConstants.DEFAULT_TIMEOUT_MS));
        jedisPool = new JedisPool(jedisPoolConfig,
                ConfigUtils.getProperty("redis.hostname"),
                NumberUtils.toInt(ConfigUtils.getProperty("redis.port"), 6390),
                NumberUtils.toInt(properties.getProperty("jedis.pool.timeout-millis"), RedisConstants.DEFAULT_TIMEOUT_MS));
    }

    private final JedisPool jedisPool;

    public <R> R doRedisOps(Function<Jedis, R> function) {
        try (Jedis jedis = jedisPool.getResource()) {
            return function.apply(jedis);
        }
    }
}
