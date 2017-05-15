package com.headfirst.bridge;

/**
 * @描述：将抽象与公有的实现分离（抽象即指汽车，实现指点火、发动、停止等，从汽车类中抽象成发动机）
 * @创建时间：2015-9-30 下午3:48:56
 */
public abstract class AbstractCar implements Car {
	// 持有发动机对象的引用
	public Engine engine;
	
	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void start() {
		engine.fireUp();
		engine.start();
	}

	@Override
	public void stop() {
		engine.stop();
	}

}
