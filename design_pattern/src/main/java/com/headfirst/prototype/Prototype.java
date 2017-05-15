package com.headfirst.prototype;

/**
 * @描述：原型类，实现Cloneable接口并覆写clone方法
 * @创建时间：2015-9-29 下午2:46:44
 */
public class Prototype implements Cloneable {
	@Override
	protected Object clone() {
		Prototype prototype = null;
		try {
			prototype = (Prototype) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return prototype;
	}
}
