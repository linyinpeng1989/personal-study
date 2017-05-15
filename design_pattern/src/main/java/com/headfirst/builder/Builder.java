package com.headfirst.builder;

/**
 * @描述：生成器统一接口
 * @创建时间：2015-9-28 上午9:55:15
 */
public interface Builder {
	void buildPart1();

	void buildPart2();

	Product getResult();
}
