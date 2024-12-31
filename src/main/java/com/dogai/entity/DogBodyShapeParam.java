package com.dogai.entity;

public class DogBodyShapeParam {
    private BodyPart earsBodyPart;
    private BodyPart headBodyPart;
    private BodyPart noseAndMouthBodyPart;
    private BodyPart eyesBodyPart;
    private int corner;

    public BodyPart getEarsShape() {
        return earsBodyPart;
    }

    public void setEarsShape(BodyPart earsBodyPart) {
        this.earsBodyPart = earsBodyPart;
    }

    public BodyPart getHeadShape() {
        return headBodyPart;
    }

    public void setHeadShape(BodyPart headBodyPart) {
        this.headBodyPart = headBodyPart;
    }

    public BodyPart getNoseAndMouthShape() {
        return noseAndMouthBodyPart;
    }

    public void setNoseAndMouthShape(BodyPart noseAndMouthBodyPart) {
        this.noseAndMouthBodyPart = noseAndMouthBodyPart;
    }

    public BodyPart getEyesShape() {
        return eyesBodyPart;
    }

    public void setEyesShape(BodyPart eyesBodyPart) {
        this.eyesBodyPart = eyesBodyPart;
    }

    public int getCorner() {
        return corner;
    }

    public void setCorner(int corner) {
        this.corner = corner;
    }

    @Override
    public String toString() {
        return "DogBodyShapeParam{" +
                "earsShape='" + earsBodyPart + '\'' +
                ", headShape='" + headBodyPart + '\'' +
                ", NoseAndMouthShape='" + noseAndMouthBodyPart + '\'' +
                ", eyesShape='" + eyesBodyPart + '\'' +
                ", corner='" + corner + '\'' +
                '}';
    }
}
