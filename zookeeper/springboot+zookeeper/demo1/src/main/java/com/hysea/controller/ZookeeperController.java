package com.hysea.controller;

import com.hysea.watch.MyWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ZookeeperController {

    @Autowired
    ZooKeeper zkClient;

    /**
     * curl -i http://localhost:8081/api/zookeeper
     * @return
     */
    @GetMapping("/zookeeper")
    public String getData() throws KeeperException, InterruptedException {
        String path = "/zookeeper";
        boolean watch = true;
        byte[] data = zkClient.getData(path, watch, null);
        return new String(data);
    }

    /**
     * curl -i http://localhost:8081/api/addNode/node1/nodeDate
     * @return
     */
    @GetMapping("/addNode/{nodeName}/{data}")
    public String addNode(@PathVariable("nodeName") String nodeName,
                          @PathVariable("data") String data) {
        // 创建节点的路径
        String path = "/" + nodeName;
        // 节点数据
        String d = data;
        // 权限控制
        List<ACL> aclList = ZooDefs.Ids.OPEN_ACL_UNSAFE;
        // 创建节点的类型
        CreateMode createMode = CreateMode.PERSISTENT;

        String result = null;
        try {
            result = zkClient.create(path, d.getBytes(), aclList, createMode);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * curl -i http://localhost:8081/api/getData/nodeName
     * @return
     */
    @GetMapping("/getData/{nodeName}")
    public String getData(@PathVariable("nodeName") String nodeName) {
        // 数据的描述信息，包括版本号，ACL权限，子节点信息等等
        Stat stat = new Stat();
        // 返回结果是byte[]数据，getData()方法底层会把描述信息复制到stat对象中
        byte[] bytes;
        String path = "/" + nodeName;
        try {
            bytes = zkClient.getData(path, false, stat);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        return new String(bytes) + "版本：" + stat.getVersion();
    }

    @GetMapping("/setData/{nodeName}/{data}")
    public String setData(@PathVariable("nodeName") String nodeName,
                          @PathVariable("data") String data) throws InterruptedException, KeeperException {
        String path = "/" + nodeName;
        zkClient.exists(path, new MyWatcher());
        String d = data;
        int version = 0;
        Stat stat = null;

        try {
            stat = zkClient.setData(path, d.getBytes(), version);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return stat.toString();
    }

    @GetMapping("/deleteNode/{nodeName}")
    public String deleteNode(@PathVariable("nodeName") String nodeName){
        String path = "/" + nodeName;
        int version = 0;
        try {
            zkClient.delete(path, version);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "OK!";
    }

}
