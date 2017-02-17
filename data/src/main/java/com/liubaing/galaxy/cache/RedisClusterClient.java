package com.liubaing.galaxy.cache;

import com.liubaing.galaxy.exception.ValidateException;
import com.liubaing.galaxy.util.ConfigUtils;
import com.liubaing.galaxy.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.util.HashSet;
import java.util.Set;

/**
 * Redis集群客户端
 * <p>
 * <p>使用方法：</p>
 * <code>RedisClusterClient.getInstance().getJedisCluster();</code>
 *
 * @author heshuai
 * @version 2016/11/15.
 */
public class RedisClusterClient {

    private static class SingletonHolder {
        private final static RedisClusterClient INSTANCE = new RedisClusterClient();
    }

    public static RedisClusterClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private RedisClusterClient() {
        ConfigUtils.loadProperties(RedisConstants.COMMON_CONFIG);
        ConfigUtils.loadProperties("config-redis-cluster-pool.properties");
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(NumberUtils.toInt(ConfigUtils.getProperty("jedis.cluster.pool.max-total"), 128));
        config.setMaxWaitMillis(NumberUtils.toInt(ConfigUtils.getProperty("jedis.cluster.pool.max-wait-millis"), RedisConstants.DEFAULT_TIMEOUT_MS));
        config.setMinIdle(NumberUtils.toInt(ConfigUtils.getProperty("jedis.cluster.pool.max-idle"), 8));
        Set<HostAndPort> jedisClusterNode = this.parseHosts(ConfigUtils.getProperty("redis.cluster.nodes"), null);
        if (CollectionUtils.isEmpty(jedisClusterNode)) {
            throw new ValidateException("Redis Cluster配置有误");
        }
        int connectionTimeout = NumberUtils.toInt(ConfigUtils.getProperty("jedis.cluster.connection.timeout"), RedisConstants.DEFAULT_TIMEOUT_MS);
        int soTimeout = NumberUtils.toInt(ConfigUtils.getProperty("jedis.cluster.socket.timeout"), RedisConstants.DEFAULT_TIMEOUT_MS);
        int maxAttempts = NumberUtils.toInt(ConfigUtils.getProperty("redis.cluster.max-redirects"), RedisConstants.DEFAULT_REDIRECTIONS);
        this.jedisCluster = new JedisCluster(jedisClusterNode,
                connectionTimeout, soTimeout, maxAttempts,
                config);
    }

    private final JedisCluster jedisCluster;

    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    private Set<HostAndPort> parseHosts(String envHosts, Set<HostAndPort> existingHostsAndPorts) {
        if (StringUtils.isNotBlank(envHosts)) {
            String[] hostDefs = StringUtils.split(envHosts, Constants.COMMA_SEPARATOR);
            if (hostDefs.length >= 2) {
                Set<HostAndPort> envHostsAndPorts = new HashSet<>(hostDefs.length);
                for (String hostDef : hostDefs) {
                    String[] hostAndPortParts = HostAndPort.extractParts(hostDef);
                    if (2 == hostAndPortParts.length) {
                        String host = hostAndPortParts[0];
                        int port = Protocol.DEFAULT_PORT;
                        try {
                            port = Integer.parseInt(hostAndPortParts[1]);
                        } catch (final NumberFormatException nfe) {
                            //ignore
                        }
                        envHostsAndPorts.add(new HostAndPort(host, port));
                    }
                }
                if (CollectionUtils.isEmpty(existingHostsAndPorts)) {
                    return envHostsAndPorts;
                } else {
                    existingHostsAndPorts.addAll(envHostsAndPorts);
                }
            }
        }
        return existingHostsAndPorts;
    }
}
