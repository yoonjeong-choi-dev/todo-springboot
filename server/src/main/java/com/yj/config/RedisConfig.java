package com.yj.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private Integer maxIdle;
    @Value("${spring.redis.jedis.pool.max-active}")
    private Integer maxActive;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private Integer minIdle;

    @Bean
    public Jedis jedis() {
        Jedis jedis = new Jedis(host, port);
        jedis.auth(password);
        log.info(jedis.ping());

        return jedis;
    }

    @Bean
    public JedisPool getJedisPool() {

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxActive);
        config.setMinIdle(minIdle);
        config.setJmxEnabled(false);    // MXBean 문제 해결을 위한 코드
        JedisPool jedisPool = new JedisPool(config, host, port, timeout, password);


        // Preload Jedis Pool
        List<Jedis> minIdleJedisList = new ArrayList<>(config.getMinIdle());
        for (int i = 0; i < config.getMinIdle(); i++) {
            try {
                Jedis jedis = jedisPool.getResource();
                minIdleJedisList.add(jedis);
                log.info(String.format("[%d] Jedis connection : %s", i, jedis.ping()));
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
        }

        for (int i = 0; i < config.getMinIdle(); i++) {
            try {
                Jedis jedis = minIdleJedisList.get(i);
                jedis.close();
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
        }

        return jedisPool;
    }


}
