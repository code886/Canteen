package com.yuanhao.factory;

import java.util.ResourceBundle;

/**
 * 工厂：创建dao或者service实例
 * @author YuanHao
 * @since 2017年2月25日
 */
public class BeanFactory {
	
	private static ResourceBundle resourceBundle;
	
	static{
		resourceBundle = ResourceBundle.getBundle("instance");
	}
	
	/**
	 * 根据指定key 读取配置文件，获得类的全路径，创建对象
	 * @return
	 */
	public static <T> T getInstance(String key,Class<T> clazz){
		String className = resourceBundle.getString(key);
		try {
			return (T) Class.forName(className).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
