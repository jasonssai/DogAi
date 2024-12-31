package com.dogai.entity;

public class DogBodyHexParam {
    private String addressHex;
    private String earsHex;
    private String headHex;
    private String NoseAndMouthHex;
    private String eyesHex;
    private String cornerHex;

    public String getAddressHex() {
        return addressHex;
    }

    public void setAddressHex(String addressHex) {
        this.addressHex = addressHex;
    }

    public String getEarsHex() {
        return earsHex;
    }

    public void setEarsHex(String earsHex) {
        this.earsHex = earsHex;
    }

    public String getHeadHex() {
        return headHex;
    }

    public void setHeadHex(String headHex) {
        this.headHex = headHex;
    }

    public String getNoseAndMouthHex() {
        return NoseAndMouthHex;
    }

    public void setNoseAndMouthHex(String noseAndMouthHex) {
        NoseAndMouthHex = noseAndMouthHex;
    }

    public String getEyesHex() {
        return eyesHex;
    }

    public void setEyesHex(String eyesHex) {
        this.eyesHex = eyesHex;
    }

    public String getCornerHex() {
        return cornerHex;
    }

    public void setCornerHex(String cornerHex) {
        this.cornerHex = cornerHex;
    }

    @Override
    public String toString() {
        return "DogBodyParam{" +
                "addressHex='" + addressHex + '\'' +
                ", earsHex='" + earsHex + '\'' +
                ", headHex='" + headHex + '\'' +
                ", NoseAndMouthHex='" + NoseAndMouthHex + '\'' +
                ", eyesHex='" + eyesHex + '\'' +
                ", cornerHex='" + cornerHex + '\'' +
                '}';
    }
}
