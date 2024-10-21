package com.hysea.config;

import com.hysea.watch.MyWatcher;
import org.apache.zookeeper.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ZookeeperConfig {

    @Value("${zookeeper.server}")
    private String server;

    @Value("${zookeeper.timeout}")
    private Integer timeout;


    private ZooKeeper zkClient;

    @Bean
    public ZooKeeper zkClient() throws IOException, InterruptedException, KeeperException {
        zkClient = new ZooKeeper(server, timeout, watchedEvent -> {

            Watcher.Event.KeeperState state = watchedEvent.getState();
            Watcher.Event.EventType type = watchedEvent.getType();
            System.out.println("检测到节点变化...");
            System.out.println("节点名称" + state.name());
            System.out.println("事件类型" + type.name());
            System.out.println("节点路径" + watchedEvent.getPath());

            try {
                getServerList(zkClient);
            } catch (InterruptedException | KeeperException e) {
                throw new RuntimeException(e);
            }
        });

        getServerList(zkClient);
        return zkClient;
    }

    private void getServerList(ZooKeeper zkClient) throws InterruptedException, KeeperException {
        List<String> children = zkClient.getChildren("/server", true);

        ArrayList<String> serverList = new ArrayList<>();
        for (String child : children) {
            byte[] data = zkClient.getData("/server/" + child, false, null);
            serverList.add(new String(data));
        }

        System.out.println(serverList);
    }

}
