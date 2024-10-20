package com.hysea.config;

import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ZookeeperConfig {

    @Value("${zookeeper.server1}")
    private String server1;

    @Value("${zookeeper.server2}")
    private String server2;

    @Value("${zookeeper.timeout}")
    private Integer timeout;

    @Bean("zkClient1")
    public ZooKeeper zkClient1() throws IOException {
        return new ZooKeeper(server1,timeout,watchedEvent -> {});
    }

    @Bean("zkClient2")
    public ZooKeeper zkClient2() throws IOException {
        return new ZooKeeper(server2,timeout,watchedEvent -> {});
    }

}
