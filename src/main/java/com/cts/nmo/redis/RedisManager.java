package com.cts.nmo.redis;

import java.util.Iterator;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisManager {

	public void runRedis(String message) {

		String cacheKey = "Values";
		Jedis jedis = redisConnector();
		addValuesToRedis(cacheKey, jedis, message);
		printValuesFromRedis(cacheKey, jedis);

	}

	private Jedis redisConnector() {

		Jedis jedis = new Jedis("52.42.22.103", 6379);
		System.out.println("Connected to Redis");
		return jedis;
	}

	private void addValuesToRedis(String cacheKey, Jedis jedis, String message) {

		jedis.sadd(cacheKey, message);
		System.out.println("Added Successfully !!!");
	}

	private void printValuesFromRedis(String cacheKey, Jedis jedis) {

		System.out.println("Randam Values: " + jedis.smembers(cacheKey));

	}

	private void deleteValuesFromRedis(Jedis jedis) {

		Set setVal = jedis.keys("*");
		Iterator itr = setVal.iterator();

		Set<String> keys = jedis.keys("*");
		for (String key : keys) {
			jedis.del(key);
		}

	}

}
