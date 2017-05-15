package com.headfirst.bridge;

public class TestBridge {
	public static void main(String[] args) {
		// 汽油型发动机
		Engine gasEngine = new GasolineEngine();
		// 柴油型发动机
		Engine dieselEngine = new DieselEngine();
		// 巴士
		Bus bus = new Bus();
		// 卡车
		Truck truck = new Truck();
		
		
		// 汽油型巴士
		bus.setEngine(gasEngine);
		bus.start();
		bus.loadPeople();
		bus.stop();
		
		System.out.println("-----------------------------------------");
		
		// 柴油型巴士
		bus.setEngine(dieselEngine);
		bus.start();
		bus.loadPeople();
		bus.stop();
		
		System.out.println("-----------------------------------------");
		
		// 汽油型卡车
		truck.setEngine(gasEngine);
		truck.start();
		truck.loadGoods();
		truck.stop();
		
		System.out.println("-----------------------------------------");
		
		// 柴油型卡车
		truck.setEngine(dieselEngine);
		truck.start();
		truck.loadGoods();
		truck.stop();
		
		/*
		// 汽油型巴士
		Bus gasBus = new Bus(gasEngine);
		gasBus.start();
		gasBus.loadPeople();
		gasBus.stop();
		
		// 柴油型巴士
		Bus dieselBus = new Bus(dieselEngine);
		dieselBus.start();
		gasBus.loadPeople();
		dieselBus.stop();
		
		// 汽油型卡车
		Truck gasTruck = new Truck(gasEngine);
		gasTruck.start();
		gasTruck.loadGoods();
		gasTruck.stop();
		
		// 柴油型卡车
		Truck dieselTruck = new Truck(dieselEngine);
		dieselTruck.start();
		dieselTruck.loadGoods();
		dieselTruck.stop();
		*/
		
	}
}
