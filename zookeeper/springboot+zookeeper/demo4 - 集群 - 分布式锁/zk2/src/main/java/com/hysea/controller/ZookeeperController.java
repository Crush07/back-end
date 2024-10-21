package com.hysea.controller;

import com.hysea.config.ZookeeperConfig;
import com.hysea.watch.MyWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ZookeeperController {

    @Autowired
    ZooKeeper zkClient;

    /**
     * 计数
     * curl -i http://localhost:8081/api/count
     * @return
     */
    @GetMapping("/count")
    public String getData() throws KeeperException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            ZookeeperConfig.lock(zkClient);
            System.out.println(i);
            Thread.sleep(1000);
            ZookeeperConfig.unLock(zkClient);
        }
        return "OK";
    }


}
