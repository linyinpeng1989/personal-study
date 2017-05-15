package com.headfirst.builder;

/**
 * @描述：⑴  客户创建Director对象，并用它所需要的Builder对象进行配置
 * 		   ⑵  一旦产品部件被生成，导向器就会通知生成器
 *  	   ⑶ 生成器处理导向器的请求，并将部件添加到该产品中
 *  	   ⑷ 客户从生成器中检索产品
 * @创建时间：2015-9-28 上午10:06:12
 */
public class TestClient {
	public static void main(String[] args) {
		// 导向器可以被多个建造者共用
		Director direc = new Director();
		
		Builder builder1 = new ConcreteBuilder1();
		Builder builder2 = new ConcreteBuilder2();
		
		// 构造复杂对象（每个复杂对象的组件先后生成并组装，然后完成复杂对象的构造）
		direc.construct(builder1);
		// 通过生成器对象获取构造完成的复杂对象
		Product p1 = builder1.getResult();
		p1.show();
		
		// 构造复杂对象（每个复杂对象的组件先后生成并组装，然后完成复杂对象的构造）
		direc.construct(builder2);
		// 通过生成器对象获取构造完成的复杂对象
		Product p2 = builder2.getResult();
		p2.show();
	}
}
