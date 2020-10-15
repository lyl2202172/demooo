package com.zhiyou.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;
@Component
//��̬ע���м���
public class RedisUtil {
	//����RedisCache�಻�ܱ�springֱ�ӹ��������޷�ֱ����������JedisConnectionFactory����������Ҫͨ������࣬������౻IOC����
	//����Ȼ���������ȡ��JedisConnectionFactory���󣬵���Rediscache���еķ�����JedisConnectionFactory����ע���ȥ
	@Autowired
	public void setJedisConnectionFactory(JedisConnectionFactory JedisConnectionFactory){
		RedisCache.setJedisConnectionFactory(JedisConnectionFactory);
	}
}
