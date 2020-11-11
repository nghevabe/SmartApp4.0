package com.example.smartapp.Model;

public class ColorModel {

    private String colorMessage;
    private String colorName;

    public String getColorMessage() {
        return colorMessage;
    }

    public ColorModel() {
    }

    public ColorModel(String colorMessage, String colorName) {
        this.colorMessage = colorMessage;
        this.colorName = colorName;
    }

    public void setColorMessage(String colorMessage) {
        this.colorMessage = colorMessage;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
}
