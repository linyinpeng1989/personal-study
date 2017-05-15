package com.headfirst.visitor.example3;

public abstract class Node {
	/**
	 * 接受操作
	 */
	public abstract void accept(Visitor visitor);
}