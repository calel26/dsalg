package com.lawsmat.word;

import com.lawsmat.stak.Stak;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class WordProcessor extends JPanel implements Runnable, MouseListener {

    private Thread animator;
    private Dimension d;
    private String status = "";
    private final Stak<Letter> text = new Stak<>();
    private int colorIndex = 0;
    private final Cursor cursor = new Cursor(20, Font.PLAIN, coArray[colorIndex]);

    private static final Color[] coArray = {
            new Color(255, 255, 255),
            new Color(0, 255, 255),
            new Color(255, 255, 0),
            new Color(255, 0, 255),
            new Color(0, 0, 255)
    };

    public WordProcessor(Dimension dimension) {
        setSize(dimension);
        setPreferredSize(dimension);
        addMouseListener(this);
        addKeyListener(new KeyHandler());
        setFocusable(true);
        d = getSize();

        //for animating the screen - you won't need to edit
        if (animator == null) {
            animator = new Thread(this);
            animator.start();
        }
        setDoubleBuffered(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        g2.fillRect(0, 0, (int) d.getWidth(), (int) d.getHeight());

        g2.setColor(cursor.activeColor);

        int x = 20;
        int y = 60;
        for(int i = text.size() - 1; i >= 0; i--) {
            Letter l = text.get(i);
            g2.setColor(l.color);
            g2.setFont(new Font("Menlo", l.style, l.size));
            if(text.size() - i == cursor.idx) {
                g2.drawRect(x + (int) (l.size / 1.2), y - l.size, 2, l.size);
            }
            if(l.ch != '\n') {
                g2.drawString(l.ch + "", x, y);
                x += (int) (l.size / 1.2);
            } else {
                y += 20;
                x = 20;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}

    private class KeyHandler extends KeyAdapter {
        public void keyReleased(KeyEvent e) {
            int keyr = e.getKeyCode();
        }

        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> {
                    cursor.idx = Math.max(0, cursor.idx - 1);
                    return;
                }
                case KeyEvent.VK_RIGHT -> {
                    cursor.idx = Math.min(text.size(), cursor.idx + 1);
                    return;
                }
                case KeyEvent.VK_UP -> {
                    cursor.size++;
                    return;
                }
                case KeyEvent.VK_DOWN -> {
                    cursor.size = Math.max(2, cursor.size - 1);
                    return;
                }
                case KeyEvent.VK_BACK_SPACE, KeyEvent.VK_DELETE -> {
                    if(!text.isEmpty()) {
                        text.pop();
                        cursor.idx --;
                    }
                    return;
                }
                case KeyEvent.VK_CONTROL -> {
                    colorIndex = (colorIndex + 1) % coArray.length;
                    cursor.activeColor = coArray[colorIndex];
                    return;
                }
                case KeyEvent.VK_SHIFT, KeyEvent.VK_CAPS_LOCK -> {return;}
            }
            char kkey = e.getKeyChar();
            status = " " + kkey;

            text.push(new Letter(
                    kkey,
                    cursor.size,
                    cursor.style,
                    cursor.activeColor
            ));
            cursor.idx++;
        }
    }

    public void run() {
        int animationDelay = 37;
        long time = System.currentTimeMillis();
        while (true) {
            repaint();
            try {
                time += animationDelay;
                Thread.sleep(Math.max(0, time - System.currentTimeMillis()));
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}