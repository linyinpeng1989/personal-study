package com.headfirst.builder;

/**
 * @描述：导向器，用于构造一个使用Builder接口实例的对象
 * @创建时间：2015-9-28 上午10:02:34
 */
public class Director {
	public void construct(Builder builder) {
		builder.buildPart1();
		builder.buildPart2();
	}
}
