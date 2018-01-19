package com.cherrypicks.tcc.cms.merchant.config;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.database}")
    private int database;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.pool.max-active}")
    private int poolMaxActive;
    @Value("${spring.redis.pool.max-wait}")
    private int poolMaxWait;
    @Value("${spring.redis.pool.max-idle}")
    private int poolMaxIdle;
    @Value("${spring.redis.pool.min-idle}")
    private int poolMinIdle;

    @Bean
    public RedisConnectionFactory jedisConnectionFactory() throws Exception {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(poolMaxActive);
        poolConfig.setMaxIdle(poolMaxIdle);
        poolConfig.setMaxWaitMillis(poolMaxWait);
        poolConfig.setMinIdle(poolMinIdle);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnCreate(true);
        poolConfig.setTestWhileIdle(true);
        final JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
        jedisConnectionFactory.setDatabase(database);
        jedisConnectionFactory.setHostName(host);
        final String pwd = password;
        if (StringUtils.isNotBlank(pwd)) {
            jedisConnectionFactory.setPassword(pwd);
        }
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setTimeout(timeout);

        // 其他配置，可再次扩展
        return jedisConnectionFactory;
    }
}
