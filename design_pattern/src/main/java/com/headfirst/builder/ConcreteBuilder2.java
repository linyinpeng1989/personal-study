package com.headfirst.builder;

public class ConcreteBuilder2 implements Builder {
	private Product product;
	
	@Override
	public void buildPart1() {
		product = new Product();
		product.add("PartX");
	}

	@Override
	public void buildPart2() {
		product.add("PartY");
	}

	@Override
	public Product getResult() {
		return product;
	}

}
