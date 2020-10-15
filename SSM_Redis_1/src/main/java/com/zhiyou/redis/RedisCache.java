package com.zhiyou.redis;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

public class RedisCache implements Cache {
	// 定义一个ID
	private final String id;
	// 创建读写锁对象
	private final ReadWriteLock rwl = new ReentrantReadWriteLock();
	// 获得一个Redis的连接
	private static JedisConnectionFactory JedisConnectionFactory;

	public static void setJedisConnectionFactory(JedisConnectionFactory JedisConnectionFactory){
		RedisCache.JedisConnectionFactory=JedisConnectionFactory;
	}
	// 通过构造方法给id赋值
	public RedisCache(String id) {
		this.id = id;
	}

	// 这个id实际上就是缓存的key
	public String getId() {
		return id;
	}

	// 将数据写入到缓存中(Redis)
	public void putObject(Object key, Object value) {
		// 写数据的时候加入锁，防止自己写的时候别人也写
		rwl.writeLock().lock();
		// 获取到Redis连接
		RedisConnection redis = JedisConnectionFactory.getConnection();
		// 用于序列化数据(key,value)
		RedisSerializer<Object> ser = new JdkSerializationRedisSerializer();
		// 将key与value序列化后存入到Redis中
		redis.set(ser.serialize(key), ser.serialize(value));
		// 关闭连接
		redis.close();
		// 写入完成后，放开锁
		rwl.writeLock().unlock();
	}

	// 从缓存中取出数据
	public Object getObject(Object key) {
		// 读锁
		rwl.readLock().lock();
		// 获取到Redis连接
		RedisConnection redis = JedisConnectionFactory.getConnection();
		// 用于序列化数据(key,value)
		RedisSerializer<Object> ser = new JdkSerializationRedisSerializer();
		// 根据序列化后的key从Redis中取值，然后在将Redis中取出的数据进行反序列化
		Object object = ser.deserialize(redis.get(ser.serialize(key)));
		System.out.println("-----" + object);
		// 关闭连接
		redis.close();
		// 写入完成后，放开锁
		rwl.readLock().unlock();
		// 将Redis中取出的数据返回
		return object;
	}

	// 从缓存中删除数据
	public Object removeObject(Object key) {
		// 读锁
		rwl.writeLock().lock();
		// 获取到Redis连接
		RedisConnection redis = JedisConnectionFactory.getConnection();
		// 用于序列化数据(key,value)
		RedisSerializer<Object> ser = new JdkSerializationRedisSerializer();
		// 根据序列化后的key将Redis中对应的key声明周期设置为0
		Boolean expire = redis.expire(ser.serialize(key), 0);
		// 关闭连接
		redis.close();
		// 写入完成后，放开锁
		rwl.writeLock().unlock();
		// 将Redis中取出的数据返回
		return expire;
	}

	// 清空缓存
	public void clear() {
		rwl.readLock().lock();
		// 获取到Redis连接
		RedisConnection redis = JedisConnectionFactory.getConnection();
		//刷新连接
		redis.flushDb();
		//刷新所有
		redis.flushAll();
		redis.close();
		rwl.readLock().unlock();
	}

	// 获得缓存中数据量
	public int getSize() {
		// 获取到Redis连接
		RedisConnection redis = JedisConnectionFactory.getConnection();
		Integer size = Integer.valueOf(redis.dbSize().toString());
		redis.close();
		return size;
	}

	public ReadWriteLock getReadWriteLock() {
		return rwl;
	}

}
