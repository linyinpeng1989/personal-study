package com.headfirst.flyweight.simple;

/**
 * @描述：抽象享元角色
 * @创建时间：2015-10-9 上午11:07:42
 */
public interface Flyweight {
	// 一个示意性方法，参数extrinsic是外蕴状态
	public void operation(String extrinsic);
}
