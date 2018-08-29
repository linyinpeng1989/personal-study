package com.headfirst.proxy.theroy.jdk;

import java.io.IOException;

/**
 * @author: Yinpeng.Lin
 * @desc:
 * @date: Created in 2018/8/29 10:56
 */
public class JdkProxyTest {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IOException {
        Person person = (Person) JdkProxyFactory.newProxyInstance(Engineer.class);
        person.doWork();
    }
}
