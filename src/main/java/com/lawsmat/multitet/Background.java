package com.lawsmat.multitet;

import java.awt.*;
import java.util.Random;

public class Background {
    public static class Ball {
        public int x;
        public int y;
        public int vx;
        public int vy;
        public int size;
        public Color color;
        public Ball(int x, int y, int vx, int vy, int size, Color color) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.size = size;
            this.color = color;
        }
    }

    private final static Random r = new Random();
    private final Ball[] bouncyBalls = new Ball[10];
    private final Dimension dims;

    public Background(Dimension dims) {
        this.dims = dims;
        for(int i = 0; i < bouncyBalls.length; i++) {
            int diameter = r.nextInt(20, 80);
            int x = r.nextInt(dims.width - diameter);
            int y = r.nextInt(dims.height - diameter);
            int vx = r.nextInt(1, 6);
            int vy = r.nextInt(1, 6);
            int colorI = r.nextInt(1, Brick.colors.length);
            var c = Brick.colors[colorI];
            bouncyBalls[i] = new Ball(x, y, vx, vy, diameter, new Color(c[0], c[1], c[2]));
        }
    }

    public void tick() {
        for(var ball : bouncyBalls) {
            if(ball.x + ball.size > dims.width || ball.x <= 0)
                ball.vx *= -1;
            if(ball.y + ball.size > dims.height || ball.y <= 0)
                ball.vy *= -1;

            ball.x += ball.vx;
            ball.y += ball.vy;
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, dims.width, dims.height);
        for(var ball : bouncyBalls) {
            g.setColor(ball.color);
            g.fillOval(ball.x, ball.y, ball.size, ball.size);
        }
    }
}
