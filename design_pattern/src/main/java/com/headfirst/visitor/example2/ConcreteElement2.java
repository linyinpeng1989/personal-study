package com.headfirst.visitor.example2;

/**
 * @描述：具体元素类，实现抽象元素类所声明的accept方法，通常都是visitor.visit(this)，基本上已经形成一种定式了
 * @创建时间：2015-10-27 上午11:08:29
 */
public class ConcreteElement2 extends Element {
	public void doSomething() {
		System.out.println("This is Element 2.");
	}

	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

}
