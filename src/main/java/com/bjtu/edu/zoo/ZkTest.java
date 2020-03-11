package com.bjtu.edu.zoo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ZkTest {

    private String connectSting = "master:2181,slave1:2181,slave2:2181";
    private int sessionTimeout = 2000;
    ZooKeeper zkClient;

    //初始化
    @Before
    public void initZk() throws IOException {
        zkClient = new ZooKeeper(connectSting, sessionTimeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                //是否存在节点
                try {
                    Stat exists = zkClient.exists("/gtest",true);
                    System.out.println(exists == null?"not exists":"exists");
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //获取节点
//                List<String> children;
//                try {
//                    children = zkClient.getChildren("/",true);
//                    for(String node:children){
//                        System.out.println("---:"+node);
//                    }
//                } catch (KeeperException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });
        System.out.println(zkClient);
    }

    //创建节点
    @Test
    public void create() throws KeeperException, InterruptedException {
        String create = zkClient.create("/gtest","ggg".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("***:"+create);
    }

    //获取子节点
    @Test
    public void getChildren() throws KeeperException, InterruptedException{

        List<String> children = zkClient.getChildren("/",true);
        for(String node :children){
            System.out.println(node);
        }
        Thread.sleep(Long.MAX_VALUE);
    }

    //判断节点是否存在
    @Test
    public void exists() throws KeeperException,InterruptedException{
        Stat exists = zkClient.exists("/gtest",true);
        System.out.println(exists == null?"not exists":"exists");
        Thread.sleep(Long.MAX_VALUE);
    }

}
