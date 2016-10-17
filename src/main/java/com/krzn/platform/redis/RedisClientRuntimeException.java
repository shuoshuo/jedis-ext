/*
 * Copyright (c) 2016-2020 LEJR.COM All Right Reserved
 */

package com.krzn.platform.redis;

/**
 * RedisClient运行时异常。
 * 
 * @author yangzhishuo
 * @version 1.0 
 * @date 2016年3月29日
 */
public class RedisClientRuntimeException extends RuntimeException{

	public RedisClientRuntimeException() {
		super();
	}

	public RedisClientRuntimeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public RedisClientRuntimeException(String arg0) {
		super(arg0);
	}

	public RedisClientRuntimeException(Throwable arg0) {
		super(arg0);
	}

	private static final long serialVersionUID = 1L;

}
