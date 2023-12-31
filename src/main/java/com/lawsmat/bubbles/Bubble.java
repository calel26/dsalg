package com.lawsmat.bubbles;

import java.awt.*;
import java.util.Random;

public class Bubble {
    private static final Color[] colors = {
        Color.BLUE,
        Color.RED,
        Color.GRAY,
        Color.YELLOW,
        Color.ORANGE,
        Color.GREEN,
        Color.PINK,
        Color.MAGENTA
    };
    private static final Random random = new Random();
    private final int sX;
    private final int sY;
    private int x;
    private int y;
    private int aX;
    private int aY;
    private Color color;

    public Bubble(int cx, int cy) {
        color = colors[random.nextInt(colors.length)];
        sX = random.nextInt(20, 100);
        sY = random.nextInt(20, 100);
        x = cx - (sX / 2);
        y = cy - (sY / 2);
        aX = random.nextInt(-3, 7);
        aY = random.nextInt(-3, 7);
        if(aX == 0 || aY == 0) {
            ++aX;
            ++aY;
        }
    }

    public void tick(Dimension d) {
        if(x < 0 || x + sX > d.width) {
            aX = Math.abs(aX) * (x < 0 ? 1 : -1);
            color = colors[random.nextInt(colors.length)];
        }
        if(y < 0 || y + sY > d.height) {
            aY = Math.abs(aY) * (y < 0 ? 1 : -1);
            color = colors[random.nextInt(colors.length)];
        }
        x += aX;
        y += aY;
    }

    public void draw(Graphics2D ctx) {
        ctx.setColor(color);
        ctx.fillOval(x, y, sX, sY);
    }
}
