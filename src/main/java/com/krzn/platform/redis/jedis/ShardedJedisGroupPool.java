/*
 * Copyright (c) 2016-2020 LEJR.COM All Right Reserved
 */

package com.krzn.platform.redis.jedis;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.Hashing;
import redis.clients.util.Pool;

/**
 * ShardedGroupJedis对象池。
 * 
 * @author yangzhishuo
 * @version 1.0 
 * @date 2016年3月30日
 */
public class ShardedJedisGroupPool extends Pool<ShardedGroupJedis>{

	public ShardedJedisGroupPool(final GenericObjectPoolConfig poolConfig, List<JedisGroupShardInfo> shards) {
		this(poolConfig, shards, Hashing.MURMUR_HASH);
	}

	public ShardedJedisGroupPool(final GenericObjectPoolConfig poolConfig, List<JedisGroupShardInfo> shards,
			Hashing algo) {
		this(poolConfig, shards, algo, null);
	}

	public ShardedJedisGroupPool(final GenericObjectPoolConfig poolConfig, List<JedisGroupShardInfo> shards,
			Pattern keyTagPattern) {
		this(poolConfig, shards, Hashing.MURMUR_HASH, keyTagPattern);
	}

	public ShardedJedisGroupPool(final GenericObjectPoolConfig poolConfig, List<JedisGroupShardInfo> shards,
			Hashing algo, Pattern keyTagPattern) {
		super(poolConfig, new ShardedGroupJedisFactory(shards, algo, keyTagPattern));
	}

	@Override
	public ShardedGroupJedis getResource() {
		ShardedGroupJedis jedis = super.getResource();
		jedis.setDataSource(this);
		return jedis;
	}

	/**
	 * PoolableObjectFactory custom impl.
	 */
	private static class ShardedGroupJedisFactory implements PooledObjectFactory<ShardedGroupJedis> {
		private List<JedisGroupShardInfo> shards;
		private Hashing algo;
		private Pattern keyTagPattern;

		public ShardedGroupJedisFactory(List<JedisGroupShardInfo> shards, Hashing algo, Pattern keyTagPattern) {
			this.shards = shards;
			this.algo = algo;
			this.keyTagPattern = keyTagPattern;
		}

		@Override
		public PooledObject<ShardedGroupJedis> makeObject() throws Exception {
			ShardedGroupJedis jedis = new ShardedGroupJedis(shards, algo, keyTagPattern);
			return new DefaultPooledObject<ShardedGroupJedis>(jedis);
		}

		@Override
		public void destroyObject(PooledObject<ShardedGroupJedis> pooledShardedJedis) throws Exception {
			final ShardedGroupJedis shardedJedis = pooledShardedJedis.getObject();
			for (JedisGroup jedisGroup : shardedJedis.getAllShards()) {
				//关闭主节点连接。
				Jedis master = jedisGroup.getMaster();
				try {
					master.quit();
				} catch (JedisConnectionException e) {
					// ignore the exception node, so that all other normal nodes can release all connections.
				}
				try {
					master.disconnect();
				} catch (JedisConnectionException e) {
					// ignore the exception node, so that all other normal nodes can release all connections.
				}

				//关闭从节点连接。
				List<Jedis> slaves = jedisGroup.getSlaves();
				if(slaves != null && slaves.size() > 0){
					for(Jedis slave : slaves){
						try {
							slave.quit();
						} catch (JedisConnectionException e) {
							// ignore the exception node, so that all other normal nodes can release all connections.
						}
						try {
							slave.disconnect();
						} catch (JedisConnectionException e) {
							// ignore the exception node, so that all other normal nodes can release all connections.
						}
					}
				}
			}
		}

		@Override
		public boolean validateObject(PooledObject<ShardedGroupJedis> pooledShardedJedis) {
			ShardedGroupJedis jedis = pooledShardedJedis.getObject();
			for (JedisGroup jedisGroup : jedis.getAllShards()) {
				try{
					String reply = jedisGroup.getMaster().ping();
					if (!"PONG".equals(reply)) {
						return false;
					}
				}catch(Exception e){
					throw new RuntimeException("can't ping [server="+jedisGroup.getMaster().getClient().getHost()+",port="+jedisGroup.getMaster().getClient().getPort()+"]", e);
				}
			}
			return true;
		}

		@Override
		public void activateObject(PooledObject<ShardedGroupJedis> p) throws Exception {

		}

		@Override
		public void passivateObject(PooledObject<ShardedGroupJedis> p) throws Exception {

		}
	}

}
