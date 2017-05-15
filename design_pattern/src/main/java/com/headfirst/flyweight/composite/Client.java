package com.headfirst.flyweight.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述：客户端类
 * @创建时间：2015-10-9 下午2:53:22
 */
public class Client {
	public static void main(String[] args) {
		List<Character> compositeState = new ArrayList<Character>();
        compositeState.add('a');
        compositeState.add('b');
        compositeState.add('c');
        compositeState.add('a');
        compositeState.add('b');
        
        FlyweightFactory flyFactory = FlyweightFactory.getInstance();
        Flyweight compositeFly1 = flyFactory.factory(compositeState);
        Flyweight compositeFly2 = flyFactory.factory(compositeState);
        compositeFly1.operation("Composite Call");
        
        System.out.println("---------------------------------");        
        System.out.println("复合享元模式是否可以共享对象：" + (compositeFly1 == compositeFly2));
        
        Character state = 'a';
        Flyweight fly1 = flyFactory.factory(state);
        Flyweight fly2 = flyFactory.factory(state);
        System.out.println("单纯享元模式是否可以共享对象：" + (fly1 == fly2));
	}
}
