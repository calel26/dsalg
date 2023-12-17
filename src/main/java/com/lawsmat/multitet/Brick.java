package com.lawsmat.multitet;

import java.util.Arrays;
import java.util.Random;

public class Brick {
    private int x;
    private int y;
    private short[][] art;
    private final float[] color;
    private Brick rotated = null;

    private static Random random = new Random();

    public Brick rotate() {
        int rows = art.length;
        int cols = art[0].length;
        short[][] temp = new short[cols][rows];
        for(int c = 0; c < cols; c++) {
            for(int r = 0; r < rows; r++) {
                // assign new rotated shape to temp
                temp[c][r] = art[rows - 1 - r][c];
            }
        }
        var brick = new Brick(temp, color);
        var newCpoint = brick.getLocalCenterPoint();
        var cpoint = getLocalCenterPoint();
        brick.x = x + cpoint[0] - newCpoint[0];
        brick.y = y + cpoint[1] - newCpoint[1];
        return brick;
    }

    private int[] getLocalCenterPoint() {
        return new int[]{
                art[0].length / 2,
                art.length / 2
        };
    }

    private final static short[][][] arts = {
        {
            {1},
            {1},
            {1},
            {1},
        },
        {
            {1,1},
            {1,1}
        },
        {
            {1,0},
            {1,0},
            {1,1}
        },
        {
            {1,1},
            {1,0},
            {1,0}
        },
        {
            {1,0},
            {1,1},
            {0,1}
        },
        {
            {0,1},
            {1,1},
            {1,0}
        },
        {
            {0,1,0},
            {1,1,1}
        }
    };

    public final static float[][] colors = {
            {0,0,0}, // BLACK
            {0,1,0}, // G
            {1,0,0}, // R
            {0,0,1}, // B
            {1,1,0}, // Ye
            {0,1,1}, // Te
            {1,0,1}, // Pu
            {1, 1, 1} // Gr
    };

    public Brick(short[][] art, float[] color) {
        assert color.length == 3;
        this.color = color;
        this.art = art;
    }

    public static Brick random() {
        int i = random.nextInt(arts.length);
        return new Brick(arts[i], colors[i + 1]);
    }

    public short[][] getArt() {
        return art;
    }

    public float[] getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
