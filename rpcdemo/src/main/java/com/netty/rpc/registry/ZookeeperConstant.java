package com.netty.rpc.registry;

/**
 * Created by linyp on 2017/5/22.
 * Zookeeper 相关配置
 */
public interface ZookeeperConstant {
    int ZK_SESSION_TIMEOUT = 5000;

    String ZK_REGISTRY_ROOT_PATH = "/registry";

    String ZK_DATA_PATH = ZK_REGISTRY_ROOT_PATH + "/data";
}
