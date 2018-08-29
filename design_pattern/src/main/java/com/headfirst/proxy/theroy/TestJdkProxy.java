package com.headfirst.proxy.theroy;

import com.headfirst.proxy.theroy.jdk.Person;
import org.apache.commons.io.FileUtils;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.IOException;

/**
 * @author: Yinpeng.Lin
 * @desc:
 * @date: Created in 2018/8/29 14:04
 */
public class TestJdkProxy {

    public static void main(String[] args) throws IOException {
        // 动态生成代理类，类名为$Proxy，并返回字节数组写入本地磁盘的字节码文件中
        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy", new Class[]{Person.class});
        File file = new File("D:/Proxy0.class");
        FileUtils.writeByteArrayToFile(file, bytes);
    }

}

// 反编译Proxy0.class字节码文件，如下

// import com.headfirst.proxy.theroy.jdk.Person;
// import java.lang.reflect.InvocationHandler;
// import java.lang.reflect.Method;
// import java.lang.reflect.Proxy;
// import java.lang.reflect.UndeclaredThrowableException;
//
// public final class $Proxy extends Proxy implements Person {
//     private static Method m1;
//     private static Method m2;
//     private static Method m3;
//     private static Method m0;
//
//     public $Proxy(InvocationHandler var1) throws  {
//         super(var1);
//     }
//
//     public final boolean equals(Object var1) throws  {
//         try {
//             return (Boolean)super.h.invoke(this, m1, new Object[]{var1});
//         } catch (RuntimeException | Error var3) {
//             throw var3;
//         } catch (Throwable var4) {
//             throw new UndeclaredThrowableException(var4);
//         }
//     }
//
//     public final String toString() throws  {
//         try {
//             return (String)super.h.invoke(this, m2, (Object[])null);
//         } catch (RuntimeException | Error var2) {
//             throw var2;
//         } catch (Throwable var3) {
//             throw new UndeclaredThrowableException(var3);
//         }
//     }
//
//     public final void doWork() throws  {
//         try {
//             super.h.invoke(this, m3, (Object[])null);
//         } catch (RuntimeException | Error var2) {
//             throw var2;
//         } catch (Throwable var3) {
//             throw new UndeclaredThrowableException(var3);
//         }
//     }
//
//     public final int hashCode() throws  {
//         try {
//             return (Integer)super.h.invoke(this, m0, (Object[])null);
//         } catch (RuntimeException | Error var2) {
//             throw var2;
//         } catch (Throwable var3) {
//             throw new UndeclaredThrowableException(var3);
//         }
//     }
//
//     static {
//         try {
//             m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
//             m2 = Class.forName("java.lang.Object").getMethod("toString");
//             m3 = Class.forName("com.headfirst.proxy.theroy.jdk.Person").getMethod("doWork");
//             m0 = Class.forName("java.lang.Object").getMethod("hashCode");
//         } catch (NoSuchMethodException var2) {
//             throw new NoSuchMethodError(var2.getMessage());
//         } catch (ClassNotFoundException var3) {
//             throw new NoClassDefFoundError(var3.getMessage());
//         }
//     }
// }
