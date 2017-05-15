package com.headfirst.flyweight.simple;

/**
 * @描述：具体享元角色
 * @创建时间：2015-10-9 上午11:09:19
 */
public class ConcreteFlyweight implements Flyweight {
	// 内蕴状态
	private Character intrinsicState = null;  
	
	/**
	 * 构造函数，内蕴状态作为参数传入
	 * @param intrinsicState
	 */
	public ConcreteFlyweight(Character intrinsicState) {
		this.intrinsicState = intrinsicState;
	}
	
	/**
	 * 外蕴状态作为参数传入方法中，改变方法的行为，但并不改变对象的内蕴状态
	 */
	@Override
	public void operation(String extrinsic) {
		System.out.println("Intrinsic State = " + this.intrinsicState);
		System.out.println("Extrinsic State = " + extrinsic);
	}

}
