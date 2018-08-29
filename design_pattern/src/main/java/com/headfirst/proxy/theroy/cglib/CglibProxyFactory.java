package com.headfirst.proxy.theroy.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author: Yinpeng.Lin
 * @desc:
 * @date: Created in 2018/8/29 13:32
 */
public class CglibProxyFactory {

    /**
     * 创建代理对象
     * @param clazz 被代理类
     * @return
     */
    public static Object newProxyInstance(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();
        // Cglib通过继承父类实现
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new EngineerMethodInterceptor());
        return enhancer.create();
    }

}
