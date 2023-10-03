package com.lawsmat.word;

import java.awt.*;

public class Letter {
    public char ch;
    public int size;
    public int style;
    public Color color;

    public Letter(char ch, int size, int style, Color color) {
        this.ch = ch;
        this.size = size;
        this.style = style;
        this.color = color;
    }
}