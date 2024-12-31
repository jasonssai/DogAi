package com.dogai.enums;

public enum BodyPartColor {
    C1("#FFCE9F"),
    C2("#FF8A35"),
    C3("#A7BBDE"),
    C4("#577BB9"),
    C5("#DDDFE5"),
    C6("#A2A6B3"),
    C7("#E7ABF2"),
    C8("#C76ED8"),
    C9("#FF7373"),
    C10("#E42727"),
    C11("#C3F983"),
    C12("#90D43F"),
    C13("#96F4F8"),
    C14("#55D2D8");

    private final String colorRBG;

    BodyPartColor(String colorRBG) {
        this.colorRBG = colorRBG;
    }

    public String getColorRBG() {
        return colorRBG;
    }
}
