package com.adc.idea.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

public class JavaBeanUtils {

	/**
	 * 将JavaBean属性信息转成Map
	 * 
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> describe(Object bean) throws Exception {
		Map<String, Object> beanMap = new HashMap<String, Object>();
		PropertyDescriptor[] descriptors = getPropertyDescriptors(bean);
		for (PropertyDescriptor descriptor : descriptors) {
			// 属性名作为key，属性值作为value放到Map中
			beanMap.put(descriptor.getName(), descriptor.getReadMethod().invoke(bean));
		}
		return beanMap;
	}

	/**
	 * 设置JavaBean属性的值
	 * 
	 * @param bean
	 * @param propName
	 *            属性名
	 * @param value
	 *            属性值
	 */
	public static void setPropertyValue(Object bean, String propName, Object value) throws Exception {
		PropertyDescriptor[] descriptors = getPropertyDescriptors(bean);
		for (PropertyDescriptor descriptor : descriptors) {
			if (propName.equals(descriptor.getName())) {
				descriptor.getWriteMethod().invoke(bean, value);
				break;
			}
		}
	}

	/**
	 * 获取JavaBean的属性性
	 * 
	 * @param bean
	 * @param propName
	 *            属性名
	 * @return
	 */
	public static Object getPropertyValue(Object bean, String propName) throws Exception {
		PropertyDescriptor[] descriptors = getPropertyDescriptors(bean);
		for (PropertyDescriptor descriptor : descriptors) {
			if (propName.equals(descriptor.getName())) {
				return descriptor.getReadMethod().invoke(bean);
			}
		}
		return null;
	}

	/**
	 * 获取PropertyDescriptor实例数组
	 * 
	 * @param bean
	 * @return
	 */
	private static PropertyDescriptor[] getPropertyDescriptors(Object bean) throws Exception {
		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
		return beanInfo.getPropertyDescriptors();
	}

}
