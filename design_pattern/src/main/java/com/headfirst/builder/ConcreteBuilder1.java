package com.headfirst.builder;

public class ConcreteBuilder1 implements Builder {
	private Product product;
	
	// part1与part2分别属于复杂对象的一部分，且有确定的装配顺序，因此可以在buildPart1中初始化product对象
	@Override
	public void buildPart1() {
		product = new Product();
		product.add("PartA");
	}

	@Override
	public void buildPart2() {
		product.add("PartB");
	}

	@Override
	public Product getResult() {
		return product;
	}

}
