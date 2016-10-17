/*
 * Copyright (c) 2016-2020 LEJR.COM All Right Reserved
 */

package com.krzn.platform.redis.jedis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisShardInfo;

/**
 * 分片信息工厂。
 * 
 * @author yangzhishuo
 * @version 1.0 
 * @date 2016年9月5日
 */
public class ShardInfoFactory {

	public static List<JedisGroupShardInfo> createJedisGroupShardInfos(String masterSlaveUrlInfo){
		try {
			/*
			 * m=redis://10.11.147.44:7000/1,s=[redis://10.11.147.44:7010/1,redis://10.11.147.44:7020/1];
			 * m=redis://10.11.147.44:7000/1,s=[redis://10.11.147.44:7010/1,redis://10.11.147.44:7020/1];
			 */
			if(masterSlaveUrlInfo == null || masterSlaveUrlInfo.trim().length() == 0){
				throw new RuntimeException("masterSlaveUrlInfo can't be empty!");
			}
			String[] msUrlInfoArray = masterSlaveUrlInfo.split(";");
			List<JedisGroupShardInfo> groupShardInfos = new ArrayList<JedisGroupShardInfo>(msUrlInfoArray.length);
			int gourpIndex = 0;
			for(String msUrlInfo : msUrlInfoArray){
				int firstIndex = msUrlInfo.indexOf(",");
				JedisShardInfo masterInfo = null;
				List<JedisShardInfo> slaveInfos = null;
				if(firstIndex > 0){
					String mpart = msUrlInfo.substring(0, firstIndex);
					masterInfo = new JedisShardInfo(mpart.split("=")[1]);
					String spart = msUrlInfo.substring(firstIndex);
					String[] surls = spart.substring(spart.indexOf("[") + 1, spart.indexOf("]")).split(",");
					if(surls.length > 0){
						slaveInfos = new ArrayList<JedisShardInfo>();
						for(String surl : surls){
							JedisShardInfo slaveInfo = new JedisShardInfo(surl);
							slaveInfos.add(slaveInfo);
						}
					}
				}else{
					masterInfo = new JedisShardInfo(msUrlInfo.split("=")[1]);
				}
				JedisGroupShardInfo groupShardInfo = new JedisGroupShardInfo();
				groupShardInfo.setMasterInfo(masterInfo);
				groupShardInfo.setSlaveInfos(slaveInfos);
				groupShardInfo.setGroupName("redis-group-" + gourpIndex);
				groupShardInfos.add(groupShardInfo);
				gourpIndex++;
			}
			return groupShardInfos;
		} catch (Exception e) {
			throw new RuntimeException("解析分片url信息失败!信息正确格式为:'m=主url,s=[从url,从url];m=主url,s=[从url,从url]'!当前info为:'"+masterSlaveUrlInfo+"'!", e);
		}
	}

	private ShardInfoFactory(){}

}
