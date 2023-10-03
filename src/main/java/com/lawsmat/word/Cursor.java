package com.lawsmat.word;

import java.awt.*;

public class Cursor {
    public int idx;
    public int size;
    public int style;
    public Color activeColor;

    public Cursor(int size, int style, Color activeColor) {
        this.size = size;
        this.style = style;
        this.activeColor = activeColor;
    }
}
