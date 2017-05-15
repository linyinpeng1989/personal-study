package com.headfirst.visitor.example2;

/**
 * @描述：具体访问者，实现抽象访问者所声明的方法，它影响到访问者访问到一个类后该干什么，要做什么事情
 * @创建时间：2015-10-27 上午11:12:54
 */
public class Visitor implements IVisitor {

	@Override
	public void visit(ConcreteElement1 el1) {
		el1.doSomething();
	}

	@Override
	public void visit(ConcreteElement2 el2) {
		el2.doSomething();
	}

}
