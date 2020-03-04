package com.example.smartapp;

public class Notificationer {

    String id;
    String type;
    String contend;

    public Notificationer(){
    }

    public Notificationer(String id, String type, String contend) {
        this.id = id;
        this.type = type;
        this.contend = contend;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContend() {
        return contend;
    }

    public void setContend(String contend) {
        this.contend = contend;
    }
}
