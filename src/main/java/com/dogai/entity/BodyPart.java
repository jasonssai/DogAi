package com.dogai.entity;

public class BodyPart {
    private int num;
    private String bodyPartColor;

    public BodyPart(int num, String bodyPartColor) {
        this.num = num;
        this.bodyPartColor = bodyPartColor;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getBodyPartColor() {
        return bodyPartColor;
    }

    public void setBodyPartColor(String bodyPartColor) {
        this.bodyPartColor = bodyPartColor;
    }

    @Override
    public String toString() {
        return "BodyPart{" +
                "num=" + num +
                ", bodyPartColor=" + bodyPartColor +
                '}';
    }
}
