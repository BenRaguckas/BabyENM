package com.goTeam.BabyENM.dao;

import com.goTeam.BabyENM.model.Node;
import com.goTeam.BabyENM.service.NodeService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SeedDB {

    @Autowired
    NodeCrudRepository crud;

    @Autowired
    NodeService nodeService;

    @PostConstruct
    public void init() {
        createNode(new Node("X1", "12.10.10.25", "Dublin", 0, 0));
        createNode(new Node("X2", "12.10.10.25", "Dublin", 0, 0));
        createNode(new Node("L1", "12.10.10.25", "Killdare", 0, 0));
        createNode(new Node("L2", "12.10.10.25", "Killdare", 0, 0));
        createNode(new Node("X1", "23.40.40.200", "Athlone", 0, 0));
        createNode(new Node("X2", "23.40.40.200", "Athlone", 0, 0));
        createNode(new Node("Marco", "12.34.56.78", "Cork", 0, 0));
        createNode(new Node("Polo", "87.65.43.21", "FootMoris", 0, 0));
        createNode(new Node("Ray", "123.123.123.123", "Cavan", 0, 0));

    }

    private Node createNode(Node node) {
        Node newN = crud.saveWithChecks(node);
        nodeService.getNodeThread(newN);
        return newN;
    };
}
