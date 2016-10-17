/*
 * Copyright (c) 2016-2020 LEJR.COM All Right Reserved
 */

package com.krzn.platform.redis.jedis;

import java.io.Closeable;
import java.util.List;
import java.util.regex.Pattern;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.Hashing;
import redis.clients.util.Pool;
import redis.clients.util.Sharded;

/**
 * 支持分片、主从的Jedis包装。
 * 
 * @author yangzhishuo
 * @version 1.0 
 * @date 2016年3月30日
 */
public class ShardedGroupJedis extends Sharded<JedisGroup, JedisGroupShardInfo> implements Closeable{

	/**
	 * 存放ShardedGroupJedis的对象池。
	 */
	protected Pool<ShardedGroupJedis> dataSource = null;

	public ShardedGroupJedis(List<JedisGroupShardInfo> shards, Hashing algo, Pattern tagPattern) {
		super(shards, algo, tagPattern);
	}

	public ShardedGroupJedis(List<JedisGroupShardInfo> shards, Hashing algo) {
		super(shards, algo);
	}

	public ShardedGroupJedis(List<JedisGroupShardInfo> shards, Pattern tagPattern) {
		super(shards, tagPattern);
	}

	public ShardedGroupJedis(List<JedisGroupShardInfo> shards) {
		super(shards);
	}

	@SuppressWarnings("deprecation")//不针对Redis3.0版本。
	@Override
	public void close() {
		if (dataSource != null) {
			boolean broken = false;

			for (JedisGroup jedisGroup : getAllShards()) {
				broken = true;
				broken  &= jedisGroup.getMaster().getClient().isBroken();
				//TODO 暂时认为只要主出现问题就认为是Broken状态。
//				List<Jedis> slaves = jedisGroup.getSlaves();
//				if(slaves != null && slaves.size() > 0){
//					for(Jedis slave : slaves){
//						broken  &= slave.getClient().isBroken();
//					}
//				}
				if (broken) {
					break;
				}
			}

			if (broken) {
				dataSource.returnBrokenResource(this);
			} else {
				dataSource.returnResource(this);
			}

		} else {
			disconnect();
		}
	}
	
	/**
	 * 关闭链接。
	 */
	public void disconnect() {

		for (JedisGroup jedisGroup : getAllShards()) {
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

	public void setDataSource(Pool<ShardedGroupJedis> shardedGroupJedisPool) {
		this.dataSource = shardedGroupJedisPool;
	}

	/**
	 * 重置状态。
	 */
	public void resetState() {
		for (JedisGroup jedisGroup : getAllShards()) {
			jedisGroup.getMaster().resetState();
			for(Jedis jedis : jedisGroup.getSlaves()){
				jedis.resetState();
			}
		}
	}

}
