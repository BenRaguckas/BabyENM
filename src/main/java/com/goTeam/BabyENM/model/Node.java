package com.goTeam.BabyENM.model;


import jakarta.persistence.*;

@Entity
@Table(name="NODES")
public class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String ip;
    private String location;
    private float longitude, latitude;


    public Node() {}

    public Node(int id, String name, String ip, String location, float longitude, float latitude) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Node(String name, String ip, String location, float longitude, float latitude) {
        this.name = name;
        this.ip = ip;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", location='" + location + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
