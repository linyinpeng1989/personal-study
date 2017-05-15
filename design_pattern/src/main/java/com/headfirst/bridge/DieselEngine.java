package com.headfirst.bridge;

/**
 * @描述：柴油型发动机
 * @创建时间：2015-9-30 下午3:45:38
 */
public class DieselEngine implements Engine {

	@Override
	public void fireUp() {
		System.out.println("柴油型发动机点火");
	}

	@Override
	public void start() {
		System.out.println("柴油型发动机启动");
	}

	@Override
	public void stop() {
		System.out.println("柴油型发动机停止运行");
	}

	@Override
	public String getDescribe() {
		return "柴油发送机";
	}

}
