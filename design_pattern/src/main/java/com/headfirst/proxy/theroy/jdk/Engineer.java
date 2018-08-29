package com.headfirst.proxy.theroy.jdk;

/**
 * @author: Yinpeng.Lin
 * @desc: 目标类，jdk动态代理需要目标对象实现接口
 * @date: Created in 2018/8/29 10:42
 */
public class Engineer implements Person {
    @Override
    public void doWork() {
        System.out.println("早上9点开始上班");
        // do some work
        System.out.println("晚上6点下班");
    }
}
