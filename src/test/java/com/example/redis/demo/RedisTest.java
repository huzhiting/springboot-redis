package com.example.redis.demo;
 
import javax.annotation.Resource;

import com.example.redis.demo.utils.RedisCounterRepository;
import com.example.redis.demo.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * redis集成测试类
 * @author huzhiting
 * @date 2020-04-22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTest {
	@Resource
	private RedisUtils redisUtils;

	@Resource
	private RedisCounterRepository redisCounterRepository;
 
	/**
	 * 插入缓存数据
	 */
	@Test
	public void set() {
		redisUtils.set("redis_key", "redis_value");
	}
	
	/**
	 * 读取缓存数据
	 */
	@Test
	public void get() {
		String value = redisUtils.get("redis_key");
		System.out.println(value);
	}

	@Test
	public void generateId(){
		String id = "N" + redisCounterRepository.getNumFromRedis("900000");
		System.out.println(id);
	}
 
}