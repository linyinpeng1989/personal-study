package com.headfirst.proxy.theroy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: Yinpeng.Lin
 * @desc:
 * @date: Created in 2018/8/29 10:45
 */
public class EngineerInvocationHandler implements InvocationHandler {
    /**
     * 持有被代理对象引用
     */
    private Object target;

    public EngineerInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("我是一名工程师");
        System.out.println("上班前开机");

        // 反射调用目标类的相应方法
        method.invoke(target, args);

        System.out.println("下班后开机");
        // 没有返回值，所以返回null
        return null;
    }

}
