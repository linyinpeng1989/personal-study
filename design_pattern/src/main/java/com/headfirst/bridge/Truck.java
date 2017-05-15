package com.headfirst.bridge;

/**
 * @描述：卡车
 * @创建时间：2015-9-30 下午3:54:10
 */
public class Truck extends AbstractCar {
	/* 在抽象类中设置了get、set方法，动态获取和设定
	 * public Truck(Engine engine) {
		this.engine = engine;
	}*/
	
	public void loadGoods() {
		System.out.println(engine.getDescribe() + "的卡车载物");
	}
}
