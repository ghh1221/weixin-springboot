package com.hh.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     * @param key：productId
     * @param value 当前时间+超时时间
     */
    public boolean lock(String key, String value) {

        //setnx对应方法: setIfAbsent(key, value)，如果可以加锁返回true，不可以加锁返回false
        if(redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }


        //下面这段代码时为了解决可能出现的死锁情况
        String currentValue = redisTemplate.opsForValue().get(key);
        //如果锁过期
        if (!StringUtils.isEmpty(currentValue)
                && Long.parseLong(currentValue) < System.currentTimeMillis()) {

            //获取上一个锁的时间：重新设置锁的过期时间value，并返回上一个过期时间
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);

            //currentValue = A，两个线程的value都是B,只会有一个线程拿到锁
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 解锁
     */
    public void unlock(String key, String value) {
        try {
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e) {
            log.error("【redis分布式锁】解锁异常, {}", e);
        }
    }
}
