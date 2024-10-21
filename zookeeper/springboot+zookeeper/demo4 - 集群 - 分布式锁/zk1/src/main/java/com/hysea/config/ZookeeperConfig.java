package com.hysea.config;

import com.hysea.watch.MyWatcher;
import org.apache.zookeeper.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

@Configuration
public class ZookeeperConfig {

    private static String currentNode;
    @Value("${zookeeper.server}")
    private String server;

    @Value("${zookeeper.timeout}")
    private Integer timeout;

    @Value("${zkClient}")
    private String zkClient;

    private static CountDownLatch connectLatch = new CountDownLatch(1);
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private static String waitPath;

    private ZooKeeper zooKeeper;

    @Bean
    public ZooKeeper zkClient() throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(server, timeout, watchedEvent -> {
            if(watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected){
                connectLatch.countDown();
                System.out.println("连接成功");
            }

            if(watchedEvent.getType() == Watcher.Event.EventType.NodeDeleted && Objects.equals(watchedEvent.getPath(), waitPath)){
                countDownLatch.countDown();
                System.out.print("释放锁。。。");
            }

        });
        connectLatch.await();
        return zooKeeper;
    }


    public static void lock(ZooKeeper zooKeeper) throws InterruptedException, KeeperException {
        System.out.print("上锁");
        currentNode = zooKeeper.create("/locks/seq", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        List<String> children = zooKeeper.getChildren("/locks", false);
        Collections.sort(children);
        if(children.size() == 1){
            return;
        }else {
            int index = children.indexOf(currentNode);
            if(index == -1){
                System.out.println("异常");
            }else if(index == 0){
                return;
            }else{
                String lastNode = children.get(index - 1);
                waitPath = "/locks/" + lastNode;
                zooKeeper.getData(waitPath, true, null);
                countDownLatch.wait();
            }
        }
    }

    public static void unLock(ZooKeeper zooKeeper) throws InterruptedException, KeeperException {
        System.out.println("解锁");
        zooKeeper.delete(currentNode,0);
    }

}
