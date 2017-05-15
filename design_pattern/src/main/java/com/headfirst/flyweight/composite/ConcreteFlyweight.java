package com.headfirst.flyweight.composite;

/**
 * @描述：具体享元角色
 * @创建时间：2015-10-9 上午11:09:19
 */
public class ConcreteFlyweight implements Flyweight {
	// 内蕴状态
	private Character intrinsicState = null;  
	
	/**
	 * 构造函数，内蕴状态作为参数传入
	 * @param state
	 */
	public ConcreteFlyweight(Character state) {
		this.intrinsicState = state;
	}
	
	/**
	 * 外蕴状态作为参数传入方法中，改变方法的行为，但并不改变对象的内蕴状态
	 */
	@Override
	public void operation(String state) {
		System.out.println("Intrinsic State = " + this.intrinsicState);
		System.out.println("Extrinsic State = " + state);
	}

}
