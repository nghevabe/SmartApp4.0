package com.example.smartapp.Data.model;

public class Message {

    private String contend;
    private String type;

    public Message(){

    }

    public Message(String contend, String type){
        this.contend = contend;
        this.type = type;
    }

    public String getContend() {
        return contend;
    }

    public void setContend(String contend) {
        this.contend = contend;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
