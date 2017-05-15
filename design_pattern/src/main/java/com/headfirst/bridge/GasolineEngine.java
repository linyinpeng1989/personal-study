package com.headfirst.bridge;

/**
 * @描述：汽油型发动机
 * @创建时间：2015-9-30 下午3:43:47
 */
public class GasolineEngine implements Engine {

	@Override
	public void fireUp() {
		System.out.println("汽油型发动机点火");
	}

	@Override
	public void start() {
		System.out.println("汽油型发动机启动");
	}

	@Override
	public void stop() {
		System.out.println("汽油型发动机停止运行");
	}

	@Override
	public String getDescribe() {
		return "汽油发动机";
	}

}
