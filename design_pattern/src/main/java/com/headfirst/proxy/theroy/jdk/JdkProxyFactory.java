package com.headfirst.proxy.theroy.jdk;

import java.lang.reflect.Proxy;

/**
 * @author: Yinpeng.Lin
 * @desc: jdk动态代理类的工厂，主要用于产生代理对象
 * @date: Created in 2018/8/29 10:51
 */
public class JdkProxyFactory {

    /**
     * 创建代理类对象
     *
     * @param clazz 被代理类
     * @return
     */
    public static Object newProxyInstance(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        Object obj = clazz.newInstance();
        return Proxy.newProxyInstance(clazz.getClassLoader(),
                clazz.getInterfaces(),
                new EngineerInvocationHandler(obj));
    }

}
