/*
 * Copyright (c) 2016-2020 LEJR.COM All Right Reserved
 */

package com.krzn.platform.redis.jedis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

/**
 * Jedis组，对应一个redis主节点和一个或多个redis从节点。
 * 
 * @author yangzhishuo
 * @version 1.0 
 * @date 2016年3月30日
 */
public class JedisGroup {

	/**
	 * 对应redis主节点。
	 */
	private Jedis master;
	
	/**
	 * 对应redis从节点。
	 */
	private List<Jedis> slaves;
	
	public JedisGroup(JedisGroupShardInfo jedisGroupShardInfo) {
		JedisShardInfo masterInfo = jedisGroupShardInfo.getMasterInfo();
		this.master = new Jedis(masterInfo);
		List<JedisShardInfo> slaveInfos = jedisGroupShardInfo.getSlaveInfos();
		if(slaveInfos != null && slaveInfos.size() > 0){
			slaves = new ArrayList<Jedis>(slaveInfos.size());
			for(JedisShardInfo slaveInfo : slaveInfos){
				slaves.add(new Jedis(slaveInfo));
			}
		}
	}

	public Jedis getMaster(){
		return master;
	}
	
	public List<Jedis> getSlaves(){
		return slaves;
	}
	
}
