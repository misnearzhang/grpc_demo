package com.misnearzhang.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.RetrySleeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CreateBuilder;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by zhanglong on 2017/7/27.
 */
public class ZookConnector {
    public void create() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(3000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        client.start();
        CreateBuilder createBuilder = client.create();
        String real_path="zhanglong";
        try {
            real_path = createBuilder
                    .creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath("/zhanglong");
            System.out.println("返回值是:"+real_path);

        } catch (Exception e) {
            System.out.println("该节点已存在zhanglong");
            e.printStackTrace();
        }
        final NodeCache nodeCache = new NodeCache(client, real_path, false);
        try {
            nodeCache.start(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("当前县城:"+Thread.currentThread());
        nodeCache.getListenable().addListener(
                () -> dataChangeListener(nodeCache.getCurrentData().getData()),executor);
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {
        ZookConnector zookConnector = new ZookConnector();
        zookConnector.create();
    }


    public void dataChangeListener(byte[] data){
        System.out.println(Thread.currentThread());
        if(data.length>0) {
            System.out.println(new String(data));
        }
    }
}
