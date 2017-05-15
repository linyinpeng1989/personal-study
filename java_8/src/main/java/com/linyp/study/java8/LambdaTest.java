package com.linyp.study.java8;

/**
 * Created by linyp on 2016/9/28.
 * Lambda表达式:①代替匿名内部类（创建实例）
 *              ②配合Stream API使用
 */
public class LambdaTest {
    public static void main(String[] args) {

        // 不采用Lambda声明方式，IDE推荐使用lambda表达式
        Runnable run = new Runnable() {
            @Override
            public void run() {
                System.out.println("111111");
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("1111111111");
            }
        }).start();

        // 采用lamdba方式
        Runnable run1 = () -> System.out.println("222222");

        new Thread(() -> System.out.println("2222222222"));

        //Invoice invoice = () -> System.out.println("create invoice bean");

        // 创建一个函数式接口的匿名对象
        FunctionnalTest test = (String str) -> {
            System.out.println(str);
            System.out.println("333333");
        };
        test.test3("XXXXXXXXXXXXXXXX");

        // 调用接口默认方法
        test.defaultMethod1();
        test.defaultMethod2();

    }
}

