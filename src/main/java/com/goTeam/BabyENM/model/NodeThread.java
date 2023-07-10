package com.goTeam.BabyENM.model;

import com.goTeam.BabyENM.service.KafkaService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NodeThread implements Runnable{
    private KafkaService kafkaService;

    private boolean shutdown = false;

    private long timeout = 1000, cacheKeepAliveTime = 10000, memoryToCacheTime = 60000;
    private long lastCacheTime;

    private long id;

    private String ip, name;

    private List<String> memory = new ArrayList<>();

    private List<String> cache;

    public NodeThread(KafkaService kafkaService, long id, String ip, String name) {
        this.id = id;
        this.kafkaService = kafkaService;
        this.ip = ip;
        this.name = name;
        this.lastCacheTime = System.currentTimeMillis();
    }

    public NodeThread(KafkaService kafkaService, long id, String ip, String name, long timeout, long cacheKeepAliveTime, long memoryToCacheTime) {
        this.id = id;
        this.kafkaService = kafkaService;
        this.ip = ip;
        this.name = name;
        this.timeout = timeout;
        this.cacheKeepAliveTime = cacheKeepAliveTime;
        this.memoryToCacheTime = memoryToCacheTime;
        this.lastCacheTime = System.currentTimeMillis();
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return ip + '-' + name;
    }

    public List<String> getMemory() {
        return memory;
    }

    public List<String> getCache() {
        return cache;
    }

    private void cache() {
        this.kafkaService.sendMessage(getIdentifier(), String.valueOf(id));
        this.lastCacheTime = System.currentTimeMillis();
        this.cache = memory;
        memory = new ArrayList<>();
    }

    public void Shutdown() {
        shutdown = true;
    }



    @Override
    public void run() {
        while (!shutdown) {
            if (System.currentTimeMillis() - lastCacheTime > memoryToCacheTime)
                cache();
            if (System.currentTimeMillis() - lastCacheTime > cacheKeepAliveTime)
                cache = null;
            memory.add("New entry: " + LocalDateTime.now());
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
