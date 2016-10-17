/*
 * Copyright (c) 2016-2020 LEJR.COM All Right Reserved
 */

package com.krzn.platform.redis.jedis.loadbalance;

import java.util.List;

import redis.clients.jedis.Jedis;

/**
 * 负载均衡基类，做一些特殊值处理。
 * 
 * @author yangzhishuo
 * @version 1.0 
 * @date 2016年3月31日
 */
public abstract class AbstractLoadBalance implements LoadBalance{

	@Override
	public Jedis select(List<Jedis> jedisList) {
		if(jedisList == null || jedisList.isEmpty()){
			throw new IllegalStateException("jedis list to load balance can't be empty!");
		}
		if(jedisList.size() == 1){
			return jedisList.get(0);
		}
		return doSelect(jedisList);
	}

	protected abstract Jedis doSelect(List<Jedis> jedisList);

}
