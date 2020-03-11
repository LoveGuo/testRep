package com.bjtu.edu.zoo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class ZkServer {
    private String connectString = "master:2181,slave1:2181,slave2:2181";
    private int sessionTimeout = 2000;
    private String parentNode = "/servers";
    ZooKeeper zkClient;

    public void getConnect() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }

    public void regedit(String hostname) throws KeeperException, InterruptedException {
        String create = zkClient.create(parentNode + "/server",hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(hostname + " is online " + create);
    }

    public void bussiness() throws InterruptedException {
        System.out.println("wo lai le");
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        //建立连接
        ZkServer zk = new ZkServer();
        zk.getConnect();
        //注册
        zk.regedit("aa");
        //业务
        zk.bussiness();
    }
}
