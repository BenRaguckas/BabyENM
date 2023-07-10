package com.goTeam.BabyENM.dao;

import com.goTeam.BabyENM.model.Node;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NodeCrudRepositoryTest {
    private static final String API = "/api/node";
    static long id;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void testPostNode(){
        Node node = new Node("Name1", "1.20.20.60", "Dublin", 0, 0);

        ResponseEntity<Node> nodeResponseEntity = restTemplate.postForEntity(API , node, Node.class);
        Node responseBody = nodeResponseEntity.getBody();
        id = responseBody.getId();

        System.out.println("NODE: " + node + "\n");
        System.out.println("RESPONSE BODY: " + responseBody);
        assertEquals(HttpStatus.CREATED, nodeResponseEntity.getStatusCode());
        assertNotEquals(-1, responseBody.getId());
        assertEquals("Name1", responseBody.getName());
        assertEquals("1.20.20.60", responseBody.getIp());
        assertEquals("Dublin", responseBody.getLocation());
        assertEquals(0, responseBody.getLatitude());
        assertEquals(0, responseBody.getLongitude());
    }

    @Test
    @Order(2)
    public void testGetAll() {
        ResponseEntity<List<Node>> responseEntity = restTemplate.exchange(
                API, HttpMethod.GET, null, new ParameterizedTypeReference<List<Node>>() {} );
        List<Node> nodes = responseEntity.getBody();
//        Test response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        Test item count
        assertNotNull(nodes);
        assertEquals(10, nodes.size());
    }

    @Test
    @Order(3)
    public void testGetByIp() {
        String tempIp = "12.10.10.25";
        ResponseEntity<List<Node>> responseEntity = restTemplate.exchange(
                API + "?ip=" + tempIp, HttpMethod.GET, null, new ParameterizedTypeReference<List<Node>>() {});
        List<Node> nodes = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(nodes);
        assertEquals(4, nodes.size());
        for (Node n: nodes) {
            assertEquals(tempIp, n.getIp());
        }
    }

    @Test
    @Order(4)
    public void testGetByName(){
        String tempName = "X1";
        ResponseEntity<List<Node>> responseEntity = restTemplate.exchange(
                API + "?name=" + tempName, HttpMethod.GET, null, new ParameterizedTypeReference<List<Node>>() {});
        List<Node> nodes = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(nodes);
        assertEquals(2, nodes.size());
        for (Node n: nodes) {
            assertEquals(tempName, n.getName());
        }
    }

    @Test
    @Order(5)
    public void testGetByIpName() {
        String tempIp = "12.10.10.25";
        String tempName = "X1";
        ResponseEntity<List<Node>> responseEntity = restTemplate.exchange(
                API + "?name=" + tempName + "&ip=" + tempIp, HttpMethod.GET, null, new ParameterizedTypeReference<List<Node>>() {});
        List<Node> nodes = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
//        System.out.println("**********");
//        nodes.forEach(System.out::println);
//        Test only element from list
        Node n = nodes.get(0);
        assertEquals(1, n.getId());
        assertEquals(tempName, n.getName());
        assertEquals(tempIp, n.getIp());
        assertEquals("Dublin", n.getLocation());
        assertEquals(0, n.getLongitude());
        assertEquals(0, n.getLatitude());
    }

    @Test
    @Order(6)
    public void testGetById() {
        long id = 1;
        ResponseEntity<Node> responseEntity = restTemplate.exchange(
                API + "/" + id, HttpMethod.GET, null, new ParameterizedTypeReference<Node>() {});
        Node n = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(n);
//        Test only element from list
        assertEquals(1, n.getId());
        assertEquals("X1", n.getName());
        assertEquals("12.10.10.25", n.getIp());
        assertEquals("Dublin", n.getLocation());
        assertEquals(0, n.getLongitude());
        assertEquals(0, n.getLatitude());
    }

    @Test
    @Order(7)
    public void testPut() {

        Node editedNode = new Node("Node009", "1.20.20.80", "Dublin", 0, 0);
        restTemplate.put(API + "/" + id, editedNode);

        Node responseNode = restTemplate.getForObject(API + "/" + id, Node.class );

        assertEquals(id, responseNode.getId());
        assertEquals("Node009", responseNode.getName());
        assertEquals("1.20.20.80", responseNode.getIp());
        assertEquals("Dublin", responseNode.getLocation());
        assertEquals(0, responseNode.getLatitude());
        assertEquals(0, responseNode.getLongitude());
    }

    @Test
    @Order(8)
    public void deleteById () {
        restTemplate.delete(API + "/" + id);

        ResponseEntity<List<Node>> responseEntity = restTemplate.exchange(
                API , HttpMethod.GET, null, new ParameterizedTypeReference<List<Node>>() {} );
        List<Node> nodes = responseEntity.getBody();

        List<Node> responseBody = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @Order(9)
    public void deleteAll () {
        restTemplate.delete(API );

        ResponseEntity<List<Node>> responseEntity = restTemplate.exchange(
                API, HttpMethod.GET, null, new ParameterizedTypeReference<List<Node>>() {} );
        List<Node> nodes = responseEntity.getBody();

        List<Node> responseBody = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, responseBody.size());
    }
}