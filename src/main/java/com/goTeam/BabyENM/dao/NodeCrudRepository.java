package com.goTeam.BabyENM.dao;

import com.goTeam.BabyENM.model.Node;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

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
    @Query("SELECT n FROM Node n WHERE n.ip = ?1")
    Collection<Node> getByIp(String ip);

    @Query("SELECT n FROM Node n WHERE n.ip = ?1 AND n.name = ?2")
    Node getByIpName(String ip, String name);

    @Transactional
    @Modifying
    default Node saveWithChecks(Node node) {
        Node duplicate = getByIpName(node.getIp(), node.getName());
        if (duplicate != null)
            return duplicate;
        else
            return save(node);
    }
}
