package com.headfirst.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述：表示被构造的复杂对象
 * @创建时间：2015-9-28 上午9:48:25
 */
public class Product {
	List<String> parts = new ArrayList<>();

	public void add(String part) {
		parts.add(part);
	}

	public void show() {
		System.out.println("-----------Product Parts-----------");
		for (String part : parts) {
			System.out.println(part);
		}
	}
}
