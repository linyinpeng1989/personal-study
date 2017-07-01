package com.netty.rpc.registry;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by linyp on 2017/5/22.
 * 服务注册组件（将RPC服务注册到ZooKeeper中）
 */
public class ServiceRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistry.class);

    private CountDownLatch countDownLatch = new CountDownLatch(1);
    /** 注册地址 */
    private String registryAddress;

    public ServiceRegistry(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public void registry(String data) {
        if (data != null) {
            ZooKeeper zk = this.connectServer();
            if (zk != null) {
                createNode(zk, data);
            }
        }
    }

    /**
     * 连接ZooKeeper服务
     * @return
     */
    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(registryAddress, ZookeeperConstant.ZK_SESSION_TIMEOUT, event -> {
                // 注册一个监听事件（WatchedEvent），当连接创建完毕之后，释放闭锁
                if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    countDownLatch.countDown();
                }
            });
            // 等待闭锁，直到连接创建完成后继续执行
            countDownLatch.await();
        } catch (IOException | InterruptedException e) {
           LOGGER.error("建立连接出现错误，errMsg={}", e.getMessage(), e);
        }
        return zk;
    }

    /**
     * 在ZooKeeper服务中创建节点（类似于文件系统）
     * @param zk
     * @param data
     */
    private void createNode(ZooKeeper zk, String data) {
        try {
            byte[] bytes = data.getBytes();
            String path = zk.create(ZookeeperConstant.ZK_DATA_PATH, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            LOGGER.debug("create zookeeper node ( {} => {})", path, data);
        } catch (KeeperException | InterruptedException e) {
            LOGGER.error("创建节点出现错误，errMsg={}", e.getMessage(), e);
        }
    }
}
