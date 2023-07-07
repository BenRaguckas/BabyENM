package com.goTeam.BabyENM.dao;

import com.goTeam.BabyENM.model.Node;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NodeCrudRepository extends CrudRepository<Node, Long> {

    @Transactional
    @Modifying
    default boolean update(Node node, long id) {

        // Get the object from the db
        if (existsById(id)) {

            Node nodeFromDb = findById(id).get();

            nodeFromDb.setIp(node.getIp());
            nodeFromDb.setLatitude(node.getLatitude());
            nodeFromDb.setLongitude(node.getLongitude());
            nodeFromDb.setLocation(node.getLocation());
            nodeFromDb.setName(node.getName());
            return true;
        }
        return false;
    }
}
