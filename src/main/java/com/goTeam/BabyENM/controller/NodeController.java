package com.goTeam.BabyENM.controller;

import com.goTeam.BabyENM.dao.NodeCrudRepository;
import com.goTeam.BabyENM.model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class NodeController {

    @Autowired
    private NodeCrudRepository repository;

    @GetMapping("/node")
    public ResponseEntity<Collection<Node>> getNodes(
            @RequestParam(value = "ip", required = false) String ip,
            @RequestParam(value = "name", required = false) String name
    ) { Collection<Node> nodes;
        if (ip != null && name != null)
            nodes = Collections.singletonList(repository.findByIpName(ip, name));
        else if (ip != null)
            nodes = repository.findByIp(ip);
        else if (name != null)
            nodes = repository.findByName(name);
        else
            nodes = Streamable.of(repository.findAll()).toList();

        return ResponseEntity
                .ok()
                .body(nodes);
    }

    @GetMapping("/node/{id}")
    public ResponseEntity<Node> getNodeById(@PathVariable long id) {
        Optional<Node> optionalNode = repository.findById(id);

        // check if node was found
        if (optionalNode.isPresent()) {
            return ResponseEntity
                    .ok()
                    .body(optionalNode.get());
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @DeleteMapping("/node/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity
                    .ok()
                    .build();
        }
        return ResponseEntity
                .notFound()
                .build();

    }

    @DeleteMapping("/node")
    public ResponseEntity<Void> deleteAllNodes() {
        repository.deleteAll();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/node")
    public ResponseEntity<Node> createNode(@RequestBody Node node) {
        node = repository.save(node);
        URI uri = URI.create("/node/" + node.getId());
        return ResponseEntity
                .created(uri)
                .body(node);
    }

    @PutMapping("/node/{id}")
    public ResponseEntity<Void> putNode(@PathVariable long id, @RequestBody Node node) {
        // check if it exists
        if (repository.update(node, id)) {
            return ResponseEntity
                    .ok()
                    .build();
        }
        return ResponseEntity
                .notFound()
                .build();
    }

}
