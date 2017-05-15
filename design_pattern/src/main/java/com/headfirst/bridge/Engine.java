package com.headfirst.bridge;

/**
 * @描述：从汽车中抽象出来实现部分（点火、启动、停止等），封装成Engine接口，具体可分为汽油型、柴油型等
 * @创建时间：2015-9-30 下午3:41:05
 */
public interface Engine {
	void fireUp();

	void start();

	void stop();
	
	// 该方法与模式无关，方便结果显示
	String getDescribe();
}
