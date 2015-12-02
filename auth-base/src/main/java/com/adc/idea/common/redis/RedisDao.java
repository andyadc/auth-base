package com.adc.idea.common.redis;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings({ "rawtypes", "unchecked", "restriction" })
public class RedisDao<T, V> implements InitializingBean {

	@Autowired
	protected BaseRedis<T, V> baseRedis; // 多例

	@Override
	public void afterPropertiesSet() throws Exception {

		Class c = getClass();
		Type type = c.getGenericSuperclass();
		Class<T> valueClass = null;
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();

			Type valueType = parameterizedType[1];
			if (valueType instanceof Class<?>) {
				valueClass = (Class<T>) parameterizedType[1];
			} else if (valueType instanceof sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl) {
				sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl pType = (sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl) valueType;
				Type rawType = pType.getRawType();
				if (rawType instanceof Class<?>) {
					valueClass = (Class<T>) rawType;
				} else {
					// System.out.println("valueType.getClass():" +
					// valueType.getClass());
				}
			} else {
				// System.out.println("valueType.getClass():" +
				// valueType.getClass());
			}
		}
		if (valueClass == null) {
			throw new Exception("not support class type");
		}

		MsgpackRedisSerializer valueSerializer = new MsgpackRedisSerializer<T>(valueClass);
		baseRedis.setValueSerializer(valueSerializer);
	}
}