package com.headfirst.visitor.example2;

/**
 * @描述：抽象访问者，抽象类或者接口，声明访问者可以访问哪些元素，具体到程序中就是visit方法中的参数定义哪些对象是可以被访问的
 * @创建时间：2015-10-27 上午11:06:56
 */
public interface IVisitor {
	public void visit(ConcreteElement1 el1);

	public void visit(ConcreteElement2 el2);
}
