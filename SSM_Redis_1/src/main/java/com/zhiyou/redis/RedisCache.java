package com.zhiyou.redis;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

public class RedisCache implements Cache {
	// ����һ��ID
	private final String id;
	// ������д������
	private final ReadWriteLock rwl = new ReentrantReadWriteLock();
	// ���һ��Redis������
	private static JedisConnectionFactory JedisConnectionFactory;

	public static void setJedisConnectionFactory(JedisConnectionFactory JedisConnectionFactory){
		RedisCache.JedisConnectionFactory=JedisConnectionFactory;
	}
	// ͨ�����췽����id��ֵ
	public RedisCache(String id) {
		this.id = id;
	}

	// ���idʵ���Ͼ��ǻ����key
	public String getId() {
		return id;
	}

	// ������д�뵽������(Redis)
	public void putObject(Object key, Object value) {
		// д���ݵ�ʱ�����������ֹ�Լ�д��ʱ�����Ҳд
		rwl.writeLock().lock();
		// ��ȡ��Redis����
		RedisConnection redis = JedisConnectionFactory.getConnection();
		// �������л�����(key,value)
		RedisSerializer<Object> ser = new JdkSerializationRedisSerializer();
		// ��key��value���л�����뵽Redis��
		redis.set(ser.serialize(key), ser.serialize(value));
		// �ر�����
		redis.close();
		// д����ɺ󣬷ſ���
		rwl.writeLock().unlock();
	}

	// �ӻ�����ȡ������
	public Object getObject(Object key) {
		// ����
		rwl.readLock().lock();
		// ��ȡ��Redis����
		RedisConnection redis = JedisConnectionFactory.getConnection();
		// �������л�����(key,value)
		RedisSerializer<Object> ser = new JdkSerializationRedisSerializer();
		// �������л����key��Redis��ȡֵ��Ȼ���ڽ�Redis��ȡ�������ݽ��з����л�
		Object object = ser.deserialize(redis.get(ser.serialize(key)));
		System.out.println("-----" + object);
		// �ر�����
		redis.close();
		// д����ɺ󣬷ſ���
		rwl.readLock().unlock();
		// ��Redis��ȡ�������ݷ���
		return object;
	}

	// �ӻ�����ɾ������
	public Object removeObject(Object key) {
		// ����
		rwl.writeLock().lock();
		// ��ȡ��Redis����
		RedisConnection redis = JedisConnectionFactory.getConnection();
		// �������л�����(key,value)
		RedisSerializer<Object> ser = new JdkSerializationRedisSerializer();
		// �������л����key��Redis�ж�Ӧ��key������������Ϊ0
		Boolean expire = redis.expire(ser.serialize(key), 0);
		// �ر�����
		redis.close();
		// д����ɺ󣬷ſ���
		rwl.writeLock().unlock();
		// ��Redis��ȡ�������ݷ���
		return expire;
	}

	// ��ջ���
	public void clear() {
		rwl.readLock().lock();
		// ��ȡ��Redis����
		RedisConnection redis = JedisConnectionFactory.getConnection();
		//ˢ������
		redis.flushDb();
		//ˢ������
		redis.flushAll();
		redis.close();
		rwl.readLock().unlock();
	}

	// ��û�����������
	public int getSize() {
		// ��ȡ��Redis����
		RedisConnection redis = JedisConnectionFactory.getConnection();
		Integer size = Integer.valueOf(redis.dbSize().toString());
		redis.close();
		return size;
	}

	public ReadWriteLock getReadWriteLock() {
		return rwl;
	}

}
