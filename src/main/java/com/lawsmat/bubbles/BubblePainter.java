package com.lawsmat.bubbles;

import com.lawsmat.q.Q;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class BubblePainter extends JPanel implements Runnable, MouseListener {
    private Thread animator;
    private final Q<Bubble> bubbles = new Q<>(10);
    Dimension d;
    String str = "";
    int xPos = 0;
    int yPos = 0;

    public BubblePainter(Dimension dimension) {
        setSize(dimension);
        setPreferredSize(dimension);
        addMouseListener(this);
        addKeyListener(new KeyCodeAdapter());
        setFocusable(true);
        d = getSize();

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

        Color co = new Color(44, 47, 49);
        g2.setColor(co);
        g2.drawString("Bubbles " + str, 20, 40);

        for(int i = 0; i < bubbles.size(); i++) {
            Bubble b = bubbles.get(i);
            b.tick(d);
            b.draw(g2);
        }
    }


    public void mousePressed(MouseEvent e) {
        xPos = e.getX();
        yPos = e.getY();
        str = xPos + " " + yPos;
        Bubble b = new Bubble(xPos, yPos);
        if(bubbles.isFull()) bubbles.remove();
        bubbles.add(b);
    }

    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

    private class KeyCodeAdapter extends KeyAdapter {
        public void keyReleased(KeyEvent e) {
            int keyr = e.getKeyCode();

        }

        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            String c = KeyEvent.getKeyText(e.getKeyCode());
            str += " char " + c + " key " + key;
            // c = Character.toString((char) key);
        }
    }

    public void run() {
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();
        int animationDelay = 37;
        long time = System.currentTimeMillis();
        while (true) {// infinite loop
            repaint();
            try {
                time += animationDelay;
                Thread.sleep(Math.max(0, time - System.currentTimeMillis()));
            } catch (InterruptedException e) {
                System.out.println("bye!");
            }
        }
    }
}
