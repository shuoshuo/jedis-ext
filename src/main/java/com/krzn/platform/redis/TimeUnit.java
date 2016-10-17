/*
 * Copyright (c) 2016-2020 LEJR.COM All Right Reserved
 */

package com.krzn.platform.redis;

/**
 * 用于set命令的超时时间单位。
 * 
 * @author yangzhishuo
 * @version 1.0 
 * @date 2016年3月29日
 */
public enum TimeUnit {
	
	SECOND("EX"),
	MILLISECOND("PX");

	private String unit;
	
	private TimeUnit(String unit) {
		this.unit = unit;
	}

	public String getUnit() {
		return unit;
	}
	
}
