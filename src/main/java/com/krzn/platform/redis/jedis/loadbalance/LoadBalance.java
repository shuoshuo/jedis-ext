/*
 * Copyright (c) 2016-2020 LEJR.COM All Right Reserved
 */

package com.krzn.platform.redis.jedis.loadbalance;

import java.util.List;

import redis.clients.jedis.Jedis;

/**
 * Jedis负载均衡策略。
 * 
 * @author yangzhishuo
 * @version 1.0 
 * @date 2016年3月31日
 */
public interface LoadBalance {

	/**
	 * 从一组Jedis中选择一个。
	 * 
	 * @param jedisList 一组Jedis。
	 * @return
	 *      选出的Jedis。
	 */
	Jedis select(List<Jedis> jedisList);
	
}
