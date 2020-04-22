package com.example.redis.demo.utils;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis操作工具类
 * @author huzhiting
 * @date 2020-04-22
 */
@Component
public class RedisUtils {
 
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * 读取缓存
	 * @param key
	 * @return
	 */
	public String get(final String key) {
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 写入缓存
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, String value) {
		boolean result = false;
		try {
			redisTemplate.opsForValue().set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 更新缓存
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean getAndSet(final String key, String value) {
		boolean result = false;
		try {
			redisTemplate.opsForValue().getAndSet(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 删除缓存
	 * @param key
	 * @return
	 */
	public boolean delete(final String key) {
		boolean result = false;
		try {
			redisTemplate.delete(key);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}