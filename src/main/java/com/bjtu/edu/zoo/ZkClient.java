package com.bjtu.edu.zoo;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户端监听节点上线、下线
 */
public class ZkClient {

    private String connectString = "master:2181,slave1:2181,slave2:2181";
    private int sessionTimeout = 2000;
    private String parentNode = "/servers";

    ZooKeeper zkClient;

    public void getConnect() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                try {
                    getServerList();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getServerList() throws KeeperException, InterruptedException {
        List<String> children = zkClient.getChildren(parentNode,true);
        List<String> servers = new ArrayList<String>();
        for(String node:children){
            byte[] value = zkClient.getData(parentNode + "/" + node, false, null);
            servers.add(new String(value));
        }
        System.out.println(servers);
    }


    public void business() throws InterruptedException {
        System.out.println("wo lai le!!!");
        Thread.sleep(Long.MAX_VALUE);
    }


    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        //获取连接
        ZkClient zk = new ZkClient();
        zk.getConnect();
        //监听节点变化
        zk.getServerList();
        //业务逻辑处理
        zk.business();
    }
}
