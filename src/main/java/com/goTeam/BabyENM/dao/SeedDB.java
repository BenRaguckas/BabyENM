package com.goTeam.BabyENM.dao;

import com.goTeam.BabyENM.model.Node;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SeedDB {

    @Autowired
    NodeCrudRepository repoOrCrud;

    @PostConstruct
    public void init() {
        repoOrCrud.saveWithChecks(new Node("X1", "12.10.10.25", "Dublin", 0, 0));
        repoOrCrud.saveWithChecks(new Node("X2", "12.10.10.25", "Dublin", 0, 0));
        repoOrCrud.saveWithChecks(new Node("L1", "12.10.10.25", "Killdare", 0, 0));
        repoOrCrud.saveWithChecks(new Node("L2", "12.10.10.25", "Killdare", 0, 0));
        repoOrCrud.saveWithChecks(new Node("X1", "23.40.40.200", "Athlone", 0, 0));
        repoOrCrud.saveWithChecks(new Node("X2", "23.40.40.200", "Athlone", 0, 0));
        repoOrCrud.saveWithChecks(new Node("Marco", "12.34.56.78", "Cork", 0, 0));
        repoOrCrud.saveWithChecks(new Node("Polo", "87.65.43.21", "FootMoris", 0, 0));
        repoOrCrud.saveWithChecks(new Node("Ray", "123.123.123.123", "Cavan", 0, 0));

    }

}
