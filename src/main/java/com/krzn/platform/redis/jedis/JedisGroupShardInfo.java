/*
 * Copyright (c) 2016-2020 LEJR.COM All Right Reserved
 */

package com.krzn.platform.redis.jedis;

import java.util.List;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.util.ShardInfo;
import redis.clients.util.Sharded;

/**
 * Jedis组分片信息。
 * 
 * @author yangzhishuo
 * @version 1.0 
 * @date 2016年3月30日
 */
public class JedisGroupShardInfo extends ShardInfo<JedisGroup>{
	
	/**
	 * 组名。
	 * <p>同时用于支持getName方法，作为分片时一致性哈希算法的节点标示。
	 */
	private String groupName;
	
	/**
	 * master节点配置。
	 */
	private JedisShardInfo masterInfo;
	
	/**
	 * slave节点配置。
	 */
	private List<JedisShardInfo> slaveInfos;
	
	public JedisGroupShardInfo() {
		super(Sharded.DEFAULT_WEIGHT);
	}

	@Override
	protected JedisGroup createResource() {
		return new JedisGroup(this);
	}

	@Override
	public String getName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public JedisShardInfo getMasterInfo() {
		return masterInfo;
	}

	public List<JedisShardInfo> getSlaveInfos() {
		return slaveInfos;
	}

	public void setMasterInfo(JedisShardInfo masterInfo) {
		this.masterInfo = masterInfo;
	}

	public void setSlaveInfos(List<JedisShardInfo> slaveInfos) {
		this.slaveInfos = slaveInfos;
	}
	
}
