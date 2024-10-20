package com.hysea.controller;

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

@RestController
@RequestMapping("/api")
public class ZookeeperController {

    @Autowired
    ZooKeeper zkClient1;

    @Autowired
    ZooKeeper zkClient2;

    /**
     * curl -i http://localhost:8081/api/addNode/node1/111?nodeType=1
     * @return
     */
    @GetMapping("/addNode/{nodeName}/{data}")
    public String addNode(@PathVariable("nodeName") String nodeName,
                          @PathVariable("data") String data,
                          @RequestParam("nodeType") Integer nodeType) {
        // 创建节点的路径
        String path = "/" + nodeName.replace(".","/");
        // 节点数据
        String d = data;
        // 权限控制
        List<ACL> aclList = ZooDefs.Ids.OPEN_ACL_UNSAFE;
        // 创建节点的类型
        CreateMode createMode;
        if(nodeType == 1){
            createMode = CreateMode.PERSISTENT;
        }else{
            createMode = CreateMode.EPHEMERAL;
        }

        String result = null;
        try {
            result = zkClient1.create(path, d.getBytes(), aclList, createMode);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * curl -i http://localhost:8081/api/getData/sanguo.wuguo
     * @return
     */
    @GetMapping("/getData/{nodeName}")
    public String getData(@PathVariable("nodeName") String nodeName) {
        // 数据的描述信息，包括版本号，ACL权限，子节点信息等等
        Stat stat = new Stat();
        // 返回结果是byte[]数据，getData()方法底层会把描述信息复制到stat对象中
        byte[] bytes;
        String path = "/" + nodeName.replace(".","/");
        try {
            bytes = zkClient1.getData(path, false, stat);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        return new String(bytes) + "版本：" + stat.getVersion();
    }

    /**
     * curl -i http://localhost:8081/api/addWatcher/nodeName
     * @return
     */
    @GetMapping("/addWatcher/{nodeName}")
    public String addWatcher(@PathVariable("nodeName") String nodeName) throws InterruptedException, KeeperException {
        String path = "/" + nodeName.replace(".","/");
        zkClient1.exists(path, new MyWatcher());
        zkClient2.exists(path, new MyWatcher());
        return "OK";
    }

    /**
     * curl -i http://localhost:8081/api/setData/nodeName/111
     * @return
     */
    @GetMapping("/setData/{nodeName}/{data}")
    public String setData(@PathVariable("nodeName") String nodeName,
                          @PathVariable("data") String data) throws InterruptedException, KeeperException {
        String path = "/" + nodeName.replace(".","/");
        String d = data;

        Stat stat = new Stat();
        zkClient1.getData(path, false, stat);
        try {
            stat = zkClient1.setData(path, d.getBytes(), stat.getVersion());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return stat.toString();
    }

    @GetMapping("/deleteNode/{nodeName}")
    public String deleteNode(@PathVariable("nodeName") String nodeName) throws InterruptedException, KeeperException {
        String path = "/" + nodeName.replace(".","/");

        Stat stat = new Stat();
        zkClient1.getData(path, false, stat);
        try {
            zkClient1.delete(path, stat.getVersion());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "OK!";
    }

}
