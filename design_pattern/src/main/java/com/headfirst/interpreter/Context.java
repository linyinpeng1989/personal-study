package com.headfirst.interpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * @描述：环境角色，定义出从变量到布尔值的一个映射
 * @创建时间：2015-10-26 上午10:03:13
 */
public class Context {

	private Map<Variable, Boolean> map = new HashMap<Variable, Boolean>();

	public void assign(Variable var, boolean value) {
		map.put(var, new Boolean(value));
	}

	public boolean lookup(Variable var) throws IllegalArgumentException {
		Boolean value = map.get(var);
		if (value == null) {
			throw new IllegalArgumentException();
		}
		return value.booleanValue();
	}
}
