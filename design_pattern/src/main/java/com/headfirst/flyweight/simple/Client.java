package com.headfirst.flyweight.simple;

/**
 * @描述：客户端类
 * @创建时间：2015-10-9 下午2:53:22
 */
public class Client {
	public static void main(String[] args) {
		FlyweightFactory factory = FlyweightFactory.getInstance();
        Flyweight fly = factory.factory(new Character('a'));
        fly.operation("First Call");
        
        fly = factory.factory(new Character('b'));
        fly.operation("Second Call");
        
        fly = factory.factory(new Character('a'));
        fly.operation("Third Call");	
	}
}
