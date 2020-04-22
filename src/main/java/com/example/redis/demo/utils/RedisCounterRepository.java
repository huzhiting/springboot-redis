package com.example.redis.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * redis生成唯一id操作类
 * @author huzhiting
 * @date 2020-04-22
 */
@Repository
@Slf4j
public class RedisCounterRepository {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 根据获取的自增数据,添加日期标识构造分布式全局唯一标识
    public String getNumFromRedis(String changeNumPrefix) {
        String dateStr = LocalDate.now().format(dateTimeFormatter);
        Long value = incrementNum(changeNumPrefix + dateStr);
        return dateStr + StringUtils.leftPad(String.valueOf(value), 6, '0');
    }

    // 从redis中获取自增数据(redis保证自增是原子操作)
    private long incrementNum(String key) {

        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        if (null == factory) {
            log.error("Unable to connect to redis.");
        }
        RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, factory);
        long increment = redisAtomicLong.incrementAndGet();
        if (1 == increment) {
            // 如果数据是初次设置,需要设置超时时间
            redisAtomicLong.expire(1, TimeUnit.DAYS);
        }
        return increment;
    }
}