package com.linyp.study.java8;

/**
 * Created by linyp on 2016/9/28.
 * 函数式接口：只包含一个抽象方法的接口（但可以包含多个默认函数实现）
 * FunctionalInterface注解用于校验函数式接口是否符合规范（只能有一个抽象方法）
 */
@FunctionalInterface
public interface FunctionnalTest {
    //void test1();
    void test3(String str);

    default void defaultMethod1(){
        System.out.println("defaultMethod1");
    }

    default void defaultMethod2(){
        System.out.println("defaultMethod2");
    }
}
