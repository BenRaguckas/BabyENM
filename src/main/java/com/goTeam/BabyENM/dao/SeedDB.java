package com.goTeam.BabyENM.dao;

import com.goTeam.BabyENM.model.Node;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SeedDB {

    @Autowired
    NodeCrudRepository NRC;

    @PostConstruct
    public void init() {
        NRC.saveWithChecks(new Node("Name1", "Ip1", " ", 0, 0));
        NRC.saveWithChecks(new Node("Name2", "Ip2", " ", 0, 0));
        NRC.saveWithChecks(new Node("Name3", "Ip1", " ", 0, 0));
        NRC.saveWithChecks(new Node("Name2", "Ip3", " ", 0, 0));
        NRC.saveWithChecks(new Node("Name1", "Ip2", " ", 0, 0));

    }

}