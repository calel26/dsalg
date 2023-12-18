package com.lawsmat.multitet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class MultiTet extends DoubleBufferedFrame {
    public static void main(String[] args) {
        var game = new Game();
        new MultiTet(game);
    }

    private final Game game;
    private final Background background;
    private boolean exit = false;
    private int tick = -1;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;
    private boolean rotate = false;
    private boolean next = false;
    private int targetBoard = 0;
    private boolean playing = false;

    private class Input implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            // nothing
        }

        @Override
        public void keyPressed(KeyEvent e) {
            var key = e.getKeyCode();
            switch(key) {
                case 10 -> next = true; // return key
                case 32 -> rotate = true; // space key
                case 37 -> left = true;
                case 39 -> right = true;
                case 40 -> down = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            var key = e.getKeyCode();
            switch(key) {
                case 10 -> next = false; // return key
                case 32 -> rotate = false; // space key
                case 37 -> left = false;
                case 39 -> right = false;
                case 40 -> down = false;
            }
        }
    }

    private static final Dimension dims = new Dimension(1000, 800);

    public MultiTet(Game game) {
        super(dims, "Lawsmar 𝑛-Dimensional Tetris");
        this.game = game;
        this.background = new Background(dims);
        setVisible(true);
        setLayout(null);
        addKeyListener(new Input());

        int tps = 60;

        while(!exit) {
            repaint();

            try {
                Thread.sleep(1000 / tps);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void draw(Graphics2D g) {
        tick++;
        background.tick();
        background.draw(g);

        if(!playing) {
            if(next) playing = true;
            return;
        }

        Board drawing = game.getMainBoard();
        int i = 0;
        var wentToNextBoard = false;
        while (drawing != null) {
            if(i == 0 && drawing.getEntryQueue().isEmpty()) {
                drawing.addBrick(Brick.random());
            }
            if(tick % 10 == 0) {
                drawing.tick(1, 0, false, false);
            }
            if(targetBoard == i && drawing.isActive() && tick % 7 == 0) {
                var r = 0;
                var d = 0;
                if(right) r += 1;
                if(left) r -= 1;
                if(down) d += 1;
                if(r != 0 || d != 0 || rotate)
                    drawing.tick(d, r, true, rotate);
            }
            // draw the board
            var board = drawing.getBoard();
            var cols = board[0].length;
            int start = 20+(i*(20*cols+6+5)); // (width of board * 20) + 4x border + 5px padding
            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(4));
            if(!drawing.isActive()) {
                g.setColor(Color.RED.darker());
            } else if(i == targetBoard) {
                g.setColor(Color.ORANGE);
            }
            g.drawRect(start - 2, 48, (cols * 20) + 4, (board.length * 20) + 4);
            for(int r = 0; r < board.length; r++) {
                for(int c = 0; c < board[r].length; c++) {
                    var color = board[r][c];
                    var co = new Color(
                            color[0],
                            color[1],
                            color[2],
                            drawing.isActive() ? 1.0f : 0.2f);
                    g.setColor(co);
                    g.fillRect(start + (20*c), 50+(20*r), 20, 20);
                }
            }
            drawing = drawing.getNextBoard();
            if(drawing != null && next && tick % 10 == 0 && targetBoard == i) {
                targetBoard++;
                wentToNextBoard = true;
            }
            i++;
        }
        if(!wentToNextBoard && next && tick % 10 == 0) {
            targetBoard = 0;
        }
    }
}
