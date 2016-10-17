/*
 * Copyright (c) 2016-2020 LEJR.COM All Right Reserved
 */

package com.krzn.platform.redis.support;

import redis.clients.jedis.Jedis;

/**
 * Jedis回调接口。
 * 
 * @author yangzhishuo
 * @version 1.0 
 * @date 2016年3月29日
 */
public interface JedisCallback<R> {

	/**
	 * 回调方法。
	 * 
	 * @param jedis jedis实例。
	 * @return
	 *      回调返回值。
	 */
	R call(Jedis jedis);
	
}
