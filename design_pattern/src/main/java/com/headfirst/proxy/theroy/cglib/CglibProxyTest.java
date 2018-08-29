package com.headfirst.proxy.theroy.cglib;

/**
 * @author: Yinpeng.Lin
 * @desc:
 * @date: Created in 2018/8/29 13:40
 */
public class CglibProxyTest {

    public static void main(String[] args) {
        Engineer engineer = (Engineer) CglibProxyFactory.newProxyInstance(Engineer.class);
        engineer.doWork();
    }

}
