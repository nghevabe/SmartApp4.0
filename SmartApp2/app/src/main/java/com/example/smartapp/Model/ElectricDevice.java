package com.example.smartapp.Model;

import java.util.List;

public class ElectricDevice {

    public String id;
    public String name;
    public String type;
    public String value;
    public String node;

    public ElectricDevice() {
    }

    public ElectricDevice(String id, String name, String type, String value) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public ElectricDevice(String id, String name, String type, String value, String node) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.value = value;
        this.node = node;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    private List<ElectricDevice> device;


}
