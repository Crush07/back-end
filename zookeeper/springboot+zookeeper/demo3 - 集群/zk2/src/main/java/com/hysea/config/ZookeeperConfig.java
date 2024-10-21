package com.hysea.config;

import com.hysea.watch.MyWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ZookeeperConfig {

    @Value("${zookeeper.server}")
    private String server;

    @Value("${zookeeper.timeout}")
    private Integer timeout;

    @Value("${zkClient}")
    private String zkClient;

    @Bean
    public ZooKeeper zkClient() throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = new ZooKeeper(server, timeout, watchedEvent -> {
        });
        // 创建临时且有序节点
        zooKeeper.create("/server/"+zkClient,zkClient.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        return zooKeeper;
    }

}
