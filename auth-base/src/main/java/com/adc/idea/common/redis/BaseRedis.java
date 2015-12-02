package com.adc.idea.common.redis;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings({ "rawtypes", "unchecked" })
@Scope("prototype")
public class BaseRedis<K, V> {

	@Autowired
	protected RedisTemplate<K, V> redisTemplate;

	private MsgpackRedisSerializer<V> valueSerializer;

	/**
	 * 设置键值
	 * 
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {
		BoundValueOperations valueOperations = redisTemplate.boundValueOps(key);
		valueOperations.set(valueSerializer.serialize(value));
	}

	/**
	 * 设置几分钟失效
	 * 
	 * @param key
	 * @param value
	 * @param expired
	 */
	public void put(K key, V value, long expired) {
		BoundValueOperations valueOperations = redisTemplate.boundValueOps(key);
		if (expired > 0) {
			valueOperations.set(valueSerializer.serialize(value), expired, TimeUnit.MINUTES);
		} else {
			valueOperations.set(valueSerializer.serialize(value));
		}
	}

	/**
	 * 按时间设置失效
	 * 
	 * @param key
	 * @param value
	 * @param expired
	 * @param timeUnit
	 */
	public void put(K key, V value, long expired, TimeUnit timeUnit) {
		BoundValueOperations valueOperations = redisTemplate.boundValueOps(key);
		valueOperations.set(valueSerializer.serialize(value), expired, timeUnit);
	}

	/**
	 * 根据key获取值
	 * 
	 * @param key
	 * @return
	 */
	public V get(final K key) {
		BoundValueOperations valueOperations = redisTemplate.boundValueOps(key);
		Object temp = valueOperations.get();

		byte[] bytes = (byte[]) temp;
		return valueSerializer.deserialize(bytes);
	}

	/**
	 * 按照输入KEY的规则匹配所有的KEY
	 * 
	 * @param pattern
	 * @return
	 * @author admin
	 */
	public Set<K> getKeys(final K pattern) {
		return redisTemplate.keys(pattern);
	}

	/**
	 * 判断指定key是否存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(K key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 根据key删除
	 * 
	 * @param key
	 */
	public void del(K key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * 获取redisTemplate
	 * 
	 * @return
	 */
	public RedisTemplate<K, V> getRedisTemplate() {
		return redisTemplate;
	}

	/**
	 * 设置redisTemplate
	 * 
	 * @param redisTemplate
	 */
	public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void setValueSerializer(MsgpackRedisSerializer valueSerializer) {
		this.valueSerializer = valueSerializer;
	}
}