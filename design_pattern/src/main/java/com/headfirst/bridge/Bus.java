package com.headfirst.bridge;

/**
 * @描述：巴士
 * @创建时间：2015-9-30 下午3:51:03
 */
public class Bus extends AbstractCar {
	/* 在抽象类中设置了get、set方法，动态获取和设定
	 * public Bus(Engine engine) {
		this.engine = engine;
	}*/
	
	public void loadPeople() {
		System.out.println(engine.getDescribe() + "的巴士载人");
	}
}
