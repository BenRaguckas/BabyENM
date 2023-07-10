package com.goTeam.BabyENM.service;

import com.goTeam.BabyENM.model.Node;
import com.goTeam.BabyENM.model.NodeThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NodeService {
    @Autowired
    private KafkaService kafkaService;
    private Map<String, NodeThread> threadMap = new HashMap<>();

    public NodeThread getNodeThread(Node node) {
        if (threadMap.containsKey(node.getIdentifier())) {
            return threadMap.get(node.getIdentifier());
        } else {
            NodeThread nodeThread = new NodeThread(kafkaService, node.getId(), node.getIp(), node.getName());
            new Thread(nodeThread).start();
            threadMap.put(nodeThread.getIdentifier(), nodeThread);
            return nodeThread;
        }
    }
}
