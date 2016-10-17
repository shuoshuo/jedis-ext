/*
 * Copyright (c) 2016-2020 LEJR.COM All Right Reserved
 */

package com.krzn.platform.redis.jedis.loadbalance;

import java.util.List;
import java.util.Random;

import redis.clients.jedis.Jedis;

/**
 * 随机负载均衡器。
 * 
 * @author yangzhishuo
 * @version 1.0 
 * @date 2016年3月31日
 */
public class RandomLoadBalance extends AbstractLoadBalance{

	@Override
	protected Jedis doSelect(List<Jedis> jedisList) {
		Random random = new Random();
		int index = random.nextInt(jedisList.size());
		return jedisList.get(index);
	}

}
