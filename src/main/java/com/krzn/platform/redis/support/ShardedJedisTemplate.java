/*
 * Copyright (c) 2016-2020 LEJR.COM All Right Reserved
 */

package com.krzn.platform.redis.support;

import java.text.MessageFormat;

import com.krzn.platform.redis.RedisClientRuntimeException;
import com.krzn.platform.redis.RedisContants;
import com.krzn.platform.redis.jedis.JedisGroup;
import com.krzn.platform.redis.jedis.ShardedGroupJedis;
import com.krzn.platform.redis.jedis.ShardedJedisGroupPool;
import com.krzn.platform.redis.jedis.loadbalance.LoadBalance;
import com.krzn.platform.redis.jedis.loadbalance.RandomLoadBalance;
import redis.clients.jedis.Jedis;

/**
 * ShardedJedis模板。
 * <p>封装Jedis调用过程中方法骨架，做一些申请、归还链接，分片，读写分离的逻辑等。
 * <p>暴露回调接口给外部使用。
 * 
 * @author yangzhishuo
 * @version 1.0 
 * @date 2016年3月29日
 */
public class ShardedJedisTemplate {

	/**
	 * 支持分片的jedis池。
	 */
	private ShardedJedisGroupPool shardedJedisGroupPool;
	
	/**
	 * 是否读写分离。
	 * <p>默认不进行读写分离。
	 */
	private boolean isReadFromSlave = false;
	
	private static final LoadBalance DUFAULT_LOADBALANCE = new RandomLoadBalance();
	
	/**
	 * 用于选择从节点的负载均衡器。
	 */
	private LoadBalance loadBalance;
	
	public ShardedJedisTemplate() {
	}

	public ShardedJedisTemplate(ShardedJedisGroupPool shardedJedisGroupPool) {
		this(shardedJedisGroupPool, DUFAULT_LOADBALANCE);
	}
	
	public ShardedJedisTemplate(ShardedJedisGroupPool shardedJedisGroupPool, boolean isReadFromSlave) {
		super();
		if(shardedJedisGroupPool == null){
			throw new IllegalArgumentException("shardedJedisGroupPool can't be null！");
		}
		this.shardedJedisGroupPool = shardedJedisGroupPool;
		this.isReadFromSlave = isReadFromSlave;
	}

	public ShardedJedisTemplate(ShardedJedisGroupPool shardedJedisGroupPool, LoadBalance loadBalance) {
		super();
		if(shardedJedisGroupPool == null){
			throw new IllegalArgumentException("shardedJedisGroupPool can't be null！");
		}
		if(loadBalance == null){
			throw new IllegalArgumentException("loadBalance can't be null！");
		}
		this.shardedJedisGroupPool = shardedJedisGroupPool;
		this.loadBalance = loadBalance;
	}

	public ShardedJedisTemplate(ShardedJedisGroupPool shardedJedisGroupPool, boolean isReadFromSlave, LoadBalance loadBalance) {
		super();
		if(shardedJedisGroupPool == null){
			throw new IllegalArgumentException("shardedJedisGroupPool can't be null！");
		}
		if(loadBalance == null){
			throw new IllegalArgumentException("loadBalance can't be null！");
		}
		this.shardedJedisGroupPool = shardedJedisGroupPool;
		this.loadBalance = loadBalance;
		this.isReadFromSlave = isReadFromSlave;
	}

	/**
	 * 模板执行方法。
	 * 
	 * @param key 键。
	 * @param callback 回调接口。
	 * @return 执行结果。
	 */
	public <R> R execute(String key, JedisCallback<R> callback, RW rw){
		Jedis jedis = null;
		ShardedGroupJedis shardedGroupJedis = null;
		R result = null;
		boolean isMaster = true;
		try{
			if(key == null || key.trim().length() == 0){
				throw new IllegalArgumentException("key set to redis can't be null!");
			}
			//从jedis池中申请一个jedis资源。
			shardedGroupJedis = shardedJedisGroupPool.getResource();
			//分片逻辑。
			JedisGroup jedisGroup = shardedGroupJedis.getShard(key);
			if(RW.WRITE.equals(rw)){
				jedis = jedisGroup.getMaster();
			}else{
				//判断读写分离标志。
				if(isReadFromSlave){
					jedis = loadBalance.select(jedisGroup.getSlaves());
					isMaster = false;
				}else{
					jedis = jedisGroup.getMaster();
				}
			}
			//执行回调逻辑，获取执行结果。
			result = callback.call(jedis);
		}catch(Throwable e){
			String errMsg = null;
			if(jedis == null){
				errMsg = e.getMessage();
			}else{
				//异常处理，加入服务器信息，方便程序更好的定位问题。
				errMsg = MessageFormat.format(
						RedisContants.ERR_MSG_TEMPLATE,
						e.getMessage(),
						isMaster ? "master" : "slave",
						jedis.getClient().getHost(),
						jedis.getClient().getPort());
			}

			throw new RedisClientRuntimeException(errMsg, e);
		}finally{
			//归还链接到jedis池。
			if(shardedGroupJedis != null){
				shardedGroupJedis.close();
			}
		}
		return result;
	}

	public void setShardedJedisGroupPool(ShardedJedisGroupPool shardedJedisGroupPool) {
		this.shardedJedisGroupPool = shardedJedisGroupPool;
	}

	public void setReadFromSlave(boolean isReadFromSlave) {
		this.isReadFromSlave = isReadFromSlave;
	}

	public void setLoadBalance(LoadBalance loadBalance) {
		this.loadBalance = loadBalance;
	}
	
}
