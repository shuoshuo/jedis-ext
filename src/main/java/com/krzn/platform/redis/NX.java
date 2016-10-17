/*
 * Copyright (c) 2016-2020 LEJR.COM All Right Reserved
 */

package com.krzn.platform.redis;

/**
 * 用于redis set命令的NX选项。
 * 
 * @author yangzhishuo
 * @version 1.0 
 * @date 2016年3月29日
 */
public enum NX {
	
	EXIST("XX"),
	NOTEXIST("NX");
	
	private String code;
	
	private NX(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
}
