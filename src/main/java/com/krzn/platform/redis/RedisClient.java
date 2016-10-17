/*
 * Copyright (c) 2016-2020 LEJR.COM All Right Reserved
 */

package com.krzn.platform.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

/**
 * Redis客户端。
 * 
 * @author yangzhishuo
 * @version 1.0 
 * @date 2016年3月29日
 */
public interface RedisClient {
	
	/*
	 * 单值操作====================================
	 */
	
	/**
	 * 设置键值。
	 * 
	 * @param key 键。 
	 * @param value 字符串形式的值。
	 * @throws RedisClientRuntimeException 如果设置不成功或者发生异常等。
	 */
	void set(String key, String value);
	
	/**
	 * 设置键值。
	 * 
	 * @param key 键。 
	 * @param value 字符串形式的值。
	 * @param expireSecond 超时时间(秒)。
	 * @throws RedisClientRuntimeException 如果设置不成功或者发生异常等。
	 */
	void set(String key, String value, int expireSecond);
	
	/**
	 * 设置键值。
	 * 
	 * @param key 键。 
	 * @param value 字符串形式的值。
	 * @param nx NX标示。 @see {@link NX}
	 * @return 设置成功返回true；未设置返回false；
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	boolean set(String key, String value, NX nx);
	
	/**
	 * 设置键值。
	 * 
	 * @param key 键。
	 * @param value 字符串形式的值。
	 * @param nx NX标示。 @see {@link NX}
	 * @param timeUnit 失效时间单位。 @see {@link TimeUnit} 
	 * @param expires 失效时间。
	 * @return 设置成功返回true；未设置返回false；
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	boolean set(String key, String value, NX nx, TimeUnit timeUnit, long expires);
	
	/**
	 * 设置键值。
	 * 
	 * @param key 键。 
	 * @param value 字节数组形式的值。
	 * @throws RedisClientRuntimeException 如果设置不成功或者发生异常等。
	 */
	void set(String key, byte[] value);
	
	/**
	 * 设置键值。
	 * 
	 * @param key 键。 
	 * @param value 字节数组形式的值。
	 * @param nx NX标示。 @see {@link NX}
	 * @return 设置成功返回true；未设置返回false；
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	boolean set(String key, byte[] value, NX nx);
	
	/**
	 * 设置键值。
	 * 
	 * @param key 键。
	 * @param value 字节数组形式的值。
	 * @param nx NX标示。 @see {@link NX}
	 * @param timeUnit 失效时间单位。 @see {@link TimeUnit} 
	 * @param expires 失效时间。
	 * @return 设置成功返回true；未设置返回false；
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	boolean set(String key, byte[] value, NX nx, TimeUnit timeUnit, long expires);
	
	/**
	 * 如果不存在给定的键对应的值，那么设置键值。
	 * 
	 * @param key 键。
	 * @param value 字符串形式的值。
	 * @return 设置成功返回true；未设置返回false；
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	boolean setnx(String key, String value);
	
	/**
	 * 如果不存在给定的键对应的值，那么设置键值。
	 * 
	 * @param key 键。
	 * @param value 字节数组形式的值。
	 * @return 设置成功返回true；未设置返回false；
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	boolean setnx(String key, byte[] value);
	
	/**
	 * 设置键值。
	 * 
	 * @param key 键。
	 * @param value 字符串形式的值。
	 * @param expires 失效时间(单位:秒)。
	 * @return 设置成功返回true；未设置返回false；
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	boolean setex(String key, String value, int expires);
	
	/**
	 * 设置键值。
	 * 
	 * @param key 键。
	 * @param value 字节数组形式的值。
	 * @param expires 失效时间(单位:秒)。
	 * @return 设置成功返回true；未设置返回false；
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	boolean setex(String key, byte[] value, int expires);
	
	/**
	 * 给指定的键设置值，并返回其旧值，如果不存在旧值，返回null。
	 * 
	 * @param key 键。
	 * @param value 字符串形式的值。
	 * @return 
	 *      返回key的旧值。
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	String getSet(String key, String value);
	
	/**
	 * 给指定的键设置值，并返回其旧值，如果不存在旧值，返回null。
	 * 
	 * @param key 键。
	 * @param value 字节数组形式的值。
	 * @return 
	 *      返回key的旧值。
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	byte[] getSet(String key, byte[] value);
	
	/**
	 * 通过键获取字符串形式的值。
	 * 
	 * @param key 键。
	 * @return key对应的值。
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	String getString(String key);
	
	/**
	 * 通过键获取字节数组的值。
	 * 
	 * @param key 键。
	 * @return key对应的值。
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	byte[] get(String key);
	
	/**
	 * 通过键删除对应的值。
	 * 
	 * @param key 键。
	 * @return 返回成功删除数据的个数。
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	long delete(String key);
	
	/**
	 * 判断某个键是否存在。
	 * 
	 * @param key 键。
	 * @return 如果key存在，返回true；否则返回false；
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	boolean exists(String key);
	
	/**
	 * 递增指定键对应的数值。
	 * <p>如果不存在key对应的值，那么会先将key的值设置为0，
	 * 然后执行incr操作，返回1。
	 * 
	 * @param key 键。
	 * @return
	 *      递增后key对应的值。
	 * @throws RedisClientRuntimeException 如果key对应的值不是数值
	 * 或者key对应的数值越界或者发生异常等。
	 */
	long incr(String key);
	
	/**
	 * 给指定键增加对应的数值。
	 * <p>如果不存在key对应的值，那么会先将key的值设置为0，
	 * 然后执行incrBy操作，返回increment。
	 * 
	 * @param key 键。
	 * @param increment 整型的增量。
	 * @return
	 *      增加后key对应的值。
	 * @throws RedisClientRuntimeException 如果key对应的值不是数值
	 * 或者key对应的数值越界或者发生异常等。
	 */
	long incrBy(String key, long increment);
	
	/**
	 * 给指定键增加对应的数值。
	 * <p>如果不存在key对应的值，那么会先将key的值设置为0，
	 * 然后执行incrByFloat操作，返回increment。
	 * 
	 * @param key 键。
	 * @param increment double型的增量。
	 * @return
	 *      增加后key对应的值。
	 * @throws RedisClientRuntimeException 如果key对应的值不是数值
	 * 或者key对应的数值越界或者发生异常等。
	 */
	double incrByFloat(String key, double increment);
	
	/**
	 * 递减指定键对应的数值。
	 * <p>如果不存在key对应的值，那么会先将key的值设置为0，
	 * 然后执行decr操作，返回-1。
	 * 
	 * @param key 键。
	 * @return
	 *      递减后key对应的值。
	 * @throws RedisClientRuntimeException 如果key对应的值不是数值
	 * 或者key对应的数值越界或者发生异常等。
	 */
	long decr(String key);
	
	/**
	 * 递给指定键减去对应的数值。
	 * <p>如果不存在key对应的值，那么会先将key的值设置为0，
	 * 然后执行decrBy操作，返回-decrement。
	 * 
	 * @param key 键。
	 * @param decrement 减量。
	 * @return
	 *      减少后key对应的值。
	 * @throws RedisClientRuntimeException 如果key对应的值不是数值
	 * 或者key对应的数值越界或者发生异常等。
	 */
	long decrBy(String key, long decrement);
	
	/**
	 * 给指定的键追加给定的值。
	 * 
	 * @param key 键。
	 * @param value 要追加的值。
	 * @return
	 *      追加后key对应的数据的长度。
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	long append(String key, String value);
	
	/**
	 * 给指定的键设置超时时间。
	 * 
	 * @param key 键。
	 * @param seconds 超时时间(单位:秒)。
	 * @return
	 *      如果成功设置超时，返回true；
	 *      如果key不存在或者未成功设置超时，返回false。
	 */
	boolean expire(String key, int seconds);
	
	/*
	 * 哈希表相关操作====================================
	 */
	
	/**
	 * 为键对应的哈希表设置一个字段及对应的值。
	 * 
	 * @param key 哈希表在redis中的key。
	 * @param field 哈希表中的字段。
	 * @param value field对应的字符串形式的值。
	 * @return
	 *      如果field是哈希表中的一个新字段，返回1；
	 *      如果field已经在哈希表中，值被更新，返回0；
	 * @throws RedisClientRuntimeException 如果发生异常等。     
	 */
	long hset(String key, String field, String value);
	
	/**
	 * 为键对应的哈希表设置一个字段及对应的值。
	 * 
	 * @param key 哈希表在redis中的key。
	 * @param field 哈希表中的字段。
	 * @param value field对应的字节数组形式的值。
	 * @return
	 *      如果field是哈希表中的一个新字段，返回1；
	 *      如果field已经在哈希表中，值被更新，返回0；
	 * @throws RedisClientRuntimeException 如果发生异常等。     
	 */
	long hset(String key, String field, byte[] value);
	
	/**
	 * 为键对应的哈希表设置多个字段及对应的值。
	 * 
	 * @param key 哈希表在redis中的key。
	 * @param fieldValues 多个字段及对应值组成的哈希表。
	 * @throws RedisClientRuntimeException 如果发生异常等。     
	 */
	void hmsetString(String key, Map<String, String> fieldValues);
	
	/**
	 * 为键对应的哈希表设置多个字段及对应的值。
	 * 
	 * @param key 哈希表在redis中的key。
	 * @param fieldValues 多个字段及对应值组成的哈希表。
	 * @throws RedisClientRuntimeException 如果发生异常等。     
	 */
	void hmset(String key, Map<String, byte[]> fieldValues);
	
	/**
	 * 如果指定键对应的哈希表中不存在给定的字段，
	 * 那么为这个字段设置一个值。
	 * 
	 * @param key 哈希表在redis中的key。
	 * @param field 哈希表中的字段。
	 * @param value field对应的字符串形式的值。
	 * @return
	 *      如果哈希表中不存在field，完成设置，返回1；
	 *      如果哈希表中存在field，不做任何操作，返回0；
	 * @throws RedisClientRuntimeException 如果发生异常等。     
	 */
	long hsetnx(String key, String field, String value);
	
	/**
	 * 如果指定键对应的哈希表中不存在给定的字段，
	 * 那么为这个字段设置一个值。
	 * 
	 * @param key 哈希表在redis中的key。
	 * @param field 哈希表中的字段。
	 * @param value field对应的字节数组形式的值。
	 * @return
	 *      如果哈希表中不存在field，完成设置，返回1；
	 *      如果哈希表中存在field，不做任何操作，返回0；
	 * @throws RedisClientRuntimeException 如果发生异常等。     
	 */
	long hsetnx(String key, String field, byte[] value);
	
	/**
	 * 从键对应的哈希表中获取给定字段对应的值。
	 * 
	 * @param key 哈希表在redis中的key。
	 * @param field 哈希表中的字段。
	 * @return
	 *      哈希表中field对应的字符串形式的值。
	 * @throws RedisClientRuntimeException 如果发生异常等。     
	 */
	String hgetString(String key, String field);
	
	/**
	 * 从键对应的哈希表中获取给定一批字段对应的值。
	 * 
	 * @param key 哈希表在redis中的key。
	 * @param fields 哈希表中的一批字段。
	 * @return
	 *      哈希表中fields对应的字符串形式的值的集合。
	 * @throws RedisClientRuntimeException 如果发生异常等。     
	 */
	List<String> hmgetString(String key, String... fields);
	
	/**
	 * 从键对应的哈希表中获取给定一批字段对应的值。
	 * 
	 * @param key 哈希表在redis中的key。
	 * @param fields 哈希表中的一批字段。
	 * @return
	 *      哈希表中fields对应的字节数组形式的值的集合。
	 * @throws RedisClientRuntimeException 如果发生异常等。     
	 */
	List<byte[]> hmget(String key, String... fields);
	
	/**
	 * 从键对应的哈希表中获取给定字段对应的值。
	 * 
	 * @param key 哈希表在redis中的key。
	 * @param field 哈希表中的字段。
	 * @return
	 *      哈希表中field对应的字节数组形式的值。
	 * @throws RedisClientRuntimeException 如果发生异常等。     
	 */
	byte[] hget(String key, String field);
	
	/**
	 * 获取键对应的哈希表。
	 * 
	 * @param key 键。
	 * @return key对应的哈希表。
	 * @throws RedisClientRuntimeException 如果发生异常等。     
	 */
	Map<String, String> hgetAllString(String key);
	
	/**
	 * 从键对应的哈希表中删除给定字段对应的值。
	 * 
	 * @param key 键。
	 * @param fields 哈希表中的字段。
	 * @return
	 *      返回从哈希表中删除字段的数量。
	 * @throws RedisClientRuntimeException 如果发生异常等。     
	 */
	long hdel(String key, String... fields);
	
	/**
	 * 判断键对应的哈希表中是否存在给定字段及对应的值。
	 * 
	 * @param key 键。
	 * @param field 哈希表中的字段。
	 * @return
	 *      如果存在，返回true；不存在返回false；
	 * @throws RedisClientRuntimeException 如果发生异常等。          
	 */
	boolean hexists(String key, String field);
	
	/**
	 * 获取给定键对应的哈希表中所有字段。
	 * 
	 * @param key 键。
	 * @return key对应的哈希表的所有字段的集合。
	 * @throws RedisClientRuntimeException 如果发生异常等。 
	 */
	Set<String> hkeys(String key);
	
	/**
	 * 获取给定键对应的哈希表中的字段数量。
	 * 
	 * @param key 键。
	 * @return key对应的哈希表的字段数量。
	 * @throws RedisClientRuntimeException 如果发生异常等。 
	 */
	long hlen(String key);
	
	/**
	 * 获取给定键对应的哈希表中所有字段的值。
	 * 
	 * @param key 键。
	 * @return key对应的哈希表的所有字段值的集合。
	 * @throws RedisClientRuntimeException 如果发生异常等。 
	 */
	List<String> hStringVals(String key);
	
	/*
	 * 列表相关操作=================================
	 */
	
	/**
	 * 获取给定键对应的列表的指定下标的字符串形式的值。
	 * 
	 * @param key 键。
	 * @param index 下标。
	 * @return 如果指定下标存在值，返回该值；否则返回null；
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	String lindexString(String key, long index);
	
	/**
	 * 获取给定键对应的列表的指定下标的字节数组形式的值。
	 * 
	 * @param key 键。
	 * @param index 下标。
	 * @return 如果指定下标存在值，返回该值；否则返回null；
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	byte[] lindex(String key, long index);
	
	/**
	 * 在给定键对应的列表头部插入一个或多个字符串形式的值。
	 * 
	 * @param key 键。
	 * @param values 要插入链表的值。
	 * @return 返回链表的长度。
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	long lpush(String key, String... values);
	
	/**
	 * 在给定键对应的列表头部插入一个或多个字节数组形式的值。
	 * 
	 * @param key 键。
	 * @param values 要插入链表的值。
	 * @return 返回链表的长度。
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	long lpush(String key, byte[]... values);
	
	/**
	 * 获取给定键对应的列表头部的元素值。
	 * 
	 * @param key 键。
	 * @return 链表头部的字符串形式的值。
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	String lpopString(String key);
	
	/**
	 * 获取并移除给定键对应的列表头部的元素值。
	 * 
	 * @param key 键。
	 * @return 链表头部的字节数组形式的值。
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	byte[] lpop(String key);
	
	/**
	 * 如果存在给定键对应的列表，那么在列表头部插入一个或多个字符串形式的值。
	 * 否则不做任何操作。
	 * 
	 * @param key 键。
	 * @param values 要插入列表的值。
	 * @return 如果存在给定键对应的列表，执行操作，返回列表长度；
	 *         否则不进行操作，返回0。
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	long lpushx(String key, String... values);
	
	/**
	 * 如果存在给定键对应的列表，那么在列表头部插入一个或多个字节数组形式的值。
	 * 否则不做任何操作。
	 * 
	 * @param key 键。
	 * @param values 要插入列表的值。
	 * @return 如果存在给定键对应的列表，执行操作，返回列表长度；
	 *         否则不进行操作，返回0。
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	long lpushx(String key, byte[]... values);
	
	/**
	 * 将给定键对应的列表的指定下标设置为给定的字符串形式的值。
	 * 如果不存在给定键对应的列表或者下标越界，那么产生异常。
	 * 
	 * @param key 键。
	 * @param index 下标。
	 * @param value 要设置的值。
	 * @throws RedisClientRuntimeException 
	 *                 如果不存在给定键对应的列表，
	 * 				       或者下标越界，
	 *                 或者发生异常等。
	 */
	void lset(String key, long index, String value);
	
	/**
	 * 将给定键对应的列表的指定下标设置为给定的字节数组形式的值。
	 * 如果不存在给定键对应的列表或者下标越界，那么产生异常。
	 * 
	 * @param key 键。
	 * @param index 下标。
	 * @param value 要设置的值。
	 * @throws RedisClientRuntimeException 
	 *                 如果不存在给定键对应的列表，
	 * 				       或者下标越界，
	 *                 或者发生异常等。
	 */
	void lset(String key, long index, byte[] value);
	
	/**
	 * 获取给定键对应的列表长度。
	 * 
	 * @param key 键。
	 * @return 如果存在列表，返回列表长度；否则返回0；
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	long llen(String key);
	
	/**
	 * 从给定键对应的列表头开始移除元素值等于指定value的count个元素。
	 * <p>如果count为0，则移除所有元素值等于指定value的元素。
	 * <p>如果count为负数时，则从列表尾部往前移除|count|个元素。
	 * 
	 * @param key 键。
	 * @param count 数量。
	 * @param value 目标值。
	 * @return
	 *      成功移除元素的数量。
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	long lrem(String key, long count, String value);
	
	/**
	 * 获取给定键对应的列表的指定范围的字符串形式的元素。
	 * 
	 * @param key 键。
	 * @param start 起始范围。
	 * @param end 结束返回。
	 * @return 指定范围内的元素集合。
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	List<String> lrangeString(String key, long start, long end);
	
	/**
	 * 获取给定键对应的列表的指定范围的字节数组形式的元素。
	 * 
	 * @param key 键。
	 * @param start 起始范围。
	 * @param end 结束返回。
	 * @return 指定范围内的元素集合。
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	List<byte[]> lrange(String key, long start, long end);
	
	/**
	 * 在给定键对应的列表尾部插入一个或多个字符串形式的值。
	 * 
	 * @param key 键。
	 * @param values 要插入链表的值。
	 * @return 返回链表的长度。
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	long rpush(String key, String... values);
	
	/**
	 * 在给定键对应的列表尾部插入一个或多个字节数组形式的值。
	 * 
	 * @param key 键。
	 * @param values 要插入链表的值。
	 * @return 返回链表的长度。
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	long rpush(String key, byte[]... values);
	
	/**
	 * 获取给定键对应的列表尾部的元素值。
	 * 
	 * @param key 键。
	 * @return 链表尾部的字符串形式的值。
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	String rpopString(String key);
	
	/**
	 * 获取并移除给定键对应尾部的元素值。
	 * 
	 * @param key 键。
	 * @return 链表尾部的字节数组形式的值。
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	byte[] rpop(String key);
	
	/**
	 * 如果存在给定键对应的列表，那么在列表尾部插入一个或多个字符串形式的值。
	 * 否则不做任何操作。
	 * 
	 * @param key 键。
	 * @param values 要插入列表的值。
	 * @return 如果存在给定键对应的列表，执行操作，返回列表长度；
	 *         否则不进行操作，返回0。
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	long rpushx(String key, String... values);
	
	/**
	 * 如果存在给定键对应的列表，那么在列表尾部插入一个或多个字节数组形式的值。
	 * 否则不做任何操作。
	 * 
	 * @param key 键。
	 * @param values 要插入列表的值。
	 * @return 如果存在给定键对应的列表，执行操作，返回列表长度；
	 *         否则不进行操作，返回0。
	 * @throws RedisClientRuntimeException 如果发生异常等。
	 */
	long rpushx(String key, byte[]... values);
	
	/**
	 * 获取给定键对应列表中的第一个字符串形式的元素，或者阻塞直到有可用元素。
	 * 
	 * @param key 键。
	 * @return
	 * 		列表中的第一个元素。
	 */
	String blpopString(String key);
	
	/**
	 * 获取给定键对应列表中的第一个字节数组形式的元素，或者阻塞直到有可用元素。
	 * 
	 * @param key 键。
	 * @return
	 * 		列表中的第一个元素。
	 */
	byte[] blpop(String key);
	
	/**
	 * 获取给定键对应列表中的第一个字符串形式的元素，或者阻塞直到有可用元素或者超时。
	 * 
	 * @param key 键。
	 * @param timeout 超时时间(秒)。
	 * @return
	 * 		列表中的第一个元素；如果超时返回null。
	 */
	String blpopString(String key, int timeout);
	
	/**
	 * 获取给定键对应列表中的第一个字节数组形式的元素，或者阻塞直到有可用元素或者超时。
	 * 
	 * @param key 键。
	 * @param timeout 超时时间(秒)。
	 * @return
	 * 		列表中的第一个元素；如果超时返回null。
	 */
	byte[] blpop(String key, int timeout);
	
	/**
	 * 获取给定键对应列表中的最后一个字符串形式的元素，或者阻塞直到有可用元素。
	 * 
	 * @param key 键。
	 * @return
	 * 		列表中的最后一个元素。
	 */
	String brpopString(String key);
	
	/**
	 * 获取给定键对应列表中的最后一个字节数组形式的元素，或者阻塞直到有可用元素。
	 * 
	 * @param key 键。
	 * @return
	 * 		列表中的最后一个元素。
	 */
	byte[] brpop(String key);
	
	/**
	 * 获取给定键对应列表中的最后一个字符串形式的元素，或者阻塞直到有可用元素或者超时。
	 * 
	 * @param key 键。
	 * @param timeout 超时时间(秒)。
	 * @return
	 * 		列表中的第一个元素；如果超时返回null。
	 */
	String brpopString(String key, int timeout);
	
	/**
	 * 获取给定键对应列表中的最后一个字节数组形式的元素，或者阻塞直到有可用元素或者超时。
	 * 
	 * @param key 键。
	 * @param timeout 超时时间(秒)。
	 * @return
	 * 		列表中的第一个元素；如果超时返回null。
	 */
	byte[] brpop(String key, int timeout);
	
	/*
	 * 集合相关操作=================================
	 */
	
	/**
	 * 为给定键对应的集合中添加一个或多个字符串形式的元素。
	 * 
	 * @param key 键。
	 * @param values 要添加的元素。
	 * @return 如果集合中不存在给定元素，添加成功，返回true；
	 *         如果集合中已经存在给定元素，不做任何操作，返回false。
	 * @throws RedisClientRuntimeException 如果发生异常等。        
	 */
	boolean sadd(String key, String... values);
	
	/**
	 * 为给定键对应的集合中添加一个或多个字节数组形式的元素。
	 * 
	 * @param key 键。
	 * @param values 要添加的元素。
	 * @return 如果集合中不存在给定元素，添加成功，返回true；
	 *         如果集合中已经存在给定元素，不做任何操作，返回false。
	 * @throws RedisClientRuntimeException 如果发生异常等。        
	 */
	boolean sadd(String key, byte[]... values);
	
	/**
	 * 获取给定键对应的集合元素。
	 * 
	 * @param key 键。
	 * @return 集合中所有元素
	 * @throws RedisClientRuntimeException 如果发生异常等。        
	 */
	Set<byte[]> bsmembers(final String key);
	
	/**
	 * 获取给定键对应的集合元素。
	 * 
	 * @param key 键。
	 * @return 集合中所有元素
	 * @throws RedisClientRuntimeException 如果发生异常等。        
	 */
	Set<String> smembers(final String key);
	
	/*
	 * 有序集合相关操作=================================
	 */
	
	/**
	 * 给指定键对应的有序集合添加元素。
	 * 
	 * @param key 键。
	 * @param value 字符串形式的元素。
	 * @param score 元素的分数。
	 * @return
	 *      如果集合中不存在value，成功插入集合，返回true。
	 *      如果集合中已存在value，更新集合元素，返回false。
	 */
	boolean zadd(String key, String value, double score);
	
	/**
	 * 给指定键对应的有序集合添加元素。
	 * 
	 * @param key 键。
	 * @param value 字节数组形式的元素。
	 * @param score 元素的分数。
	 * @return
	 *      如果集合中不存在value，成功插入集合，返回true。
	 *      如果集合中已存在value，更新集合元素，返回false。
	 */
	boolean zadd(String key, byte[] value, double score);
	
	/**
	 * 给指定键对应的有序集合添加一批元素。
	 * 
	 * @param key 键。
	 * @param valueScorePairs 字符串形式元素-分数对的集合。
	 * @return
	 *      返回新增到集合的元素个数，不包含更新的元素个数。
	 */
	long zaddString(String key, Map<String, Double> valueScorePairs);
	
	/**
	 * 给指定键对应的有序集合添加一批元素。
	 * 
	 * @param key 键。
	 * @param valueScorePairs 字节数组形式元素-分数对的集合。
	 * @return
	 *      返回新增到集合的元素个数，不包含更新的元素个数。
	 */
	long zadd(String key, Map<byte[], Double> valueScorePairs);
	
	/**
	 * 删除指定键对应的有序集合中的一批元素。
	 * 
	 * @param key 键。
	 * @param values 要删除的字符串形式的元素。
	 * @return
	 *      从集合中删除的元素数量。
	 */
	long zrem(String key, String... values);
	
	/**
	 * 删除指定键对应的有序集合中的一批元素。
	 * 
	 * @param key 键。
	 * @param values 要删除的字节数组形式的元素。
	 * @return
	 *      从集合中删除的元素数量。
	 */
	long zrem(String key, byte[]... values);
	
	/**
	 * 获取指定键对应的有序集合中的元素个数。
	 * 
	 * @param key 键。
	 * @return 元素个数。
	 */
	long zcard(String key);
	
	/**
	 * 从指定键对应的有序集合中获取给定下标范围的字符串形式的元素。
	 * <p>元素顺序按分数由小到大。
	 * 
	 * @param key 键。
	 * @param start 起始下标。(下标从0开始)
	 * @param end 结束下标。
	 * @return
	 *      指定下标范围内的元素集合。
	 */
	Set<String> zrangeString(String key, long start, long end);
	
	/**
	 * 从指定键对应的有序集合中获取给定下标范围的字节数组形式的元素。
	 * <p>元素顺序按分数由小到大。
	 * 
	 * @param key 键。
	 * @param start 起始下标。(下标从0开始)
	 * @param end 结束下标。
	 * @return
	 *      指定下标范围内的元素集合。
	 */
	Set<byte[]> zrange(String key, long start, long end);
	
	/**
	 * 从指定键对应的有序集合中获取给定下标范围的字符串形式的元素和对应的分数。
	 * <p>元素顺序按分数由小到大。
	 * 
	 * @param key 键。
	 * @param start 起始下标。(下标从0开始)
	 * @param end 结束下标。
	 * @return
	 *      指定下标范围内的元素-分数对集合。
	 */
	Set<Tuple> zrangeStringWithScores(String key, long start, long end);
	
	/**
	 * 从指定键对应的有序集合中获取给定下标范围的字节数组形式的元素和对应的分数。
	 * <p>元素顺序按分数由小到大。
	 * 
	 * @param key 键。
	 * @param start 起始下标。(下标从0开始)
	 * @param end 结束下标。
	 * @return
	 *      指定下标范围内的元素-分数对集合。
	 */
	Set<Tuple> zrangeWithScores(String key, long start, long end);
	
	/**
	 * 为给定键对应的有序集合中的给定元素增加分数值。
	 * 
	 * @param key 键
	 * @param value 元素
	 * @param score 分数
	 * @return
	 * 		增加后新的分数值。
	 */
	double zincrby(String key, String value, double score);
	
	/**
	 * 从指定键对应的有序集合中获取给定下标范围的字节数组形式的元素。
	 * <p>元素反序输出。
	 * 
	 * @param key 键。
	 * @param start 起始下标。(下标从0开始)
	 * @param end 结束下标。
	 * @return
	 *      指定下标范围内的元素集合。
	 */
	Set<byte[]> zrevrange(String key, long start, long end);
	
	/**
	 * 从指定键对应的有序集合中获取给定下标范围的字符串形式的元素。
	 * <p>元素反序输出。
	 * 
	 * @param key 键。
	 * @param start 起始下标。(下标从0开始)
	 * @param end 结束下标。
	 * @return
	 *      指定下标范围内的元素集合。
	 */
	Set<String> zrevrangeString(String key, long start, long end);
	
	/**
	 * 获取给定键对应的有序列表中给定元素的分数值。
	 * 
	 * @param key 键。
	 * @param value 元素。
	 * @return
	 * 		元素的分数值。
	 */
	Double zscore(String key, String value);
	
	/*
	 * 排序操作=================================
	 */
	
	/**
	 * 对指定键对应的Set或者List进行排序。
	 * <p>数据为字符串形式。
	 * <p>默认升序。
	 * 
	 * @param key 键。
	 * @return
	 * 		排序后的数据列表。
	 */
	List<String> sortString(String key);
	
	/**
	 * 对指定键对应的Set或者List进行排序。
	 * <p>数据为字节数组形式。
	 * <p>默认升序。
	 * 
	 * @param key 键。
	 * @return
	 * 		排序后的数据列表。
	 */
	List<byte[]> sort(String key);
	
	/**
	 * 对指定键对应的Set或者List进行排序。
	 * <p>数据为字符串形式。
	 * 
	 * @param key 键。
	 * @param sortOrder 顺序。
	 * @return
	 * 		排序后的数据列表。
	 */
	List<String> sortString(String key, SortOrder sortOrder);
	
	/**
	 * 对指定键对应的Set或者List进行排序。
	 * <p>数据为字节数组形式。
	 * 
	 * @param key 键。
	 * @param sortOrder 顺序。
	 * @return
	 * 		排序后的数据列表。
	 */
	List<byte[]> sort(String key, SortOrder sortOrder);
	
	/**
	 * 对指定键对应的Set或者List进行排序。
	 * <p>数据为字符串形式。
	 * 
	 * @param key 键。
	 * @param sortingParams 排序参数。
	 * @return
	 * 		排序后的数据列表。
	 */
	List<String> sortString(String key, SortingParams sortingParams);
	
	/**
	 * 对指定键对应的Set或者List进行排序。
	 * <p>数据为字节数组形式。
	 * 
	 * @param key 键。
	 * @param sortingParams 排序参数。
	 * @return
	 * 		排序后的数据列表。
	 */
	List<byte[]> sort(String key, SortingParams sortingParams);
	
	/*
	 * 统计方法====================================
	 */

	/**
	 * 获取Redis客户端统计信息。
	 * 
	 * @return
	 *      Redis客户端统计信息。
	 */
	RedisClientStatistic getStatistic();
	
}
