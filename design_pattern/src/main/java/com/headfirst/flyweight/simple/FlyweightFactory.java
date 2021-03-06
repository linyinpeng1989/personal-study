package com.headfirst.flyweight.simple;

import java.util.HashMap;
import java.util.Map;

/**
 * @描述：享元工厂角色（使用单例模式创建）
 * @创建时间：2015-10-9 下午2:43:59
 */
public class FlyweightFactory {
	// 用于存储享元对象，即挡当Flyweight pool（享元对象池）的功能
	private Map<Character, Flyweight> files = new HashMap<Character, Flyweight>();
	
	private FlyweightFactory() {

	}
	
	private static FlyweightFactory instance = new FlyweightFactory();
	
	public static FlyweightFactory getInstance(){
		return instance;
	}
	
	public Flyweight factory(Character state) {
		// 先从缓存中查找对象
		Flyweight fly = files.get(state);
		if (fly == null) {
			// 如果对象不存在则创建一个新的Flyweight对象
			fly = new ConcreteFlyweight(state);
			// 把这个新的Flyweight对象添加到缓存中
			files.put(state, fly);
		}
		return fly;
	}
	
}
