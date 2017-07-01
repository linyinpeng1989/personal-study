package com.netty.rpc.server;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by linyp on 2017/5/22.
 * 自定义注解，用于标识某个实现类为RPC接口
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component  // 表示可以被Spring扫描，加载到Spring容器中
public @interface RpcService {
    Class<?> value();
}
