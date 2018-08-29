package com.headfirst.proxy.theroy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author: Yinpeng.Lin
 * @desc:
 * @date: Created in 2018/8/29 13:38
 */
public class EngineerMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("我是一名工程师");
        System.out.println("上班前开机");

        // 调用目标类对应方法（即代理类的父类）
        methodProxy.invokeSuper(obj, args);

        System.out.println("下班后开机");
        // 没有返回值，所以返回null
        return null;
    }
}
