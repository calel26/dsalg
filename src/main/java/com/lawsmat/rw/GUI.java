package com.lawsmat.rw;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

public class GUI {
    private JFrame frame;
    private static ReadWrite rw = new ReadWrite();
    private static int idx = 0;

    public GUI() {
        frame = new JFrame("ReadWrite");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(frame.getSize());
        frame.add(new InnerProgram(frame.getSize()));
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String... argv) {
        new GUI();
    }

    public static class InnerProgram extends JPanel implements Runnable, MouseListener  {

        ArrayList<String> names = new ArrayList<>();
        private Thread animator;
        Dimension d;
        int startX = 20;
        int startY = 20;
        String nName = "";
        int prev = 0;

        public InnerProgram (Dimension dimension) {
            setSize(dimension);
            setPreferredSize(dimension);
            addMouseListener(this);
            addKeyListener(new TAdapter());
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
            Graphics2D g2 = (Graphics2D)g;

            g2.setColor(Color.black);
            Color co = new Color(54,10,42);
            g2.setColor(co);
            g2.fillRect(0, 0,(int)d.getWidth() , (int)d.getHeight());
            startX = 20;
            startY = 20;
            int sideL = 100;
            int sideW = 150;
            int count=1;


            co = new Color(255,255,255);
            g2.setColor(co);

            g2.drawString("Phrase: " + nName,220,((int)d.getHeight()-(sideL*2) + sideL/2) - 200);

        }

        public static int random (int a, int b){
            int max=a;
            int min=b;
            int random=(int)(Math.random() * (max - min) + min);

            return random;
        }

        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();



        }
        public void mouseReleased(MouseEvent e) {
        }
        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
        }

        private class TAdapter extends KeyAdapter {

            public void keyReleased(KeyEvent e) {
                int keyr = e.getKeyCode();

            }

            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                String c = KeyEvent.getKeyText(e.getKeyCode());
                c = Character.toString((char) key);

                if(prev!=16){
                    c = c.toLowerCase();
                }

                prev = key;
                if(key==8)
                    nName = nName.substring(0,nName.length()-1);
                if(key==10){
                    rw.writeToFile(nName);
                    nName="";
                }

                if(key==37) {
                    ArrayList<String> names = rw.read();
                    idx = Math.max(0, idx--);
                    nName = names.get(idx);
                } else if(key==39) {
                    ArrayList<String> names = rw.read();
                    System.out.println(names.size());
                    idx = Math.min(names.size() - 1, idx++);
                    nName = names.get(idx);
                }

                System.out.println("i" + idx);

                if(key!=8 && key!=10 && key!=16 && key!=37 && key!=39)
                    nName += c;
                System.out.println( key + " - " +  c);

                // message = "Key Pressed: " + e.getKeyCode();
            }
        }//end of adapter

        public void run() {
            long beforeTime, timeDiff, sleep;
            beforeTime = System.currentTimeMillis();
            int animationDelay = 37;
            long time = System.currentTimeMillis();
            while (true) {// infinite loop
                // spriteManager.update();
                repaint();
                try {
                    time += animationDelay;
                    Thread.sleep(Math.max(0, time - System.currentTimeMillis()));
                } catch (InterruptedException e) {
                    System.out.println(e);
                } // end catch
            } // end while loop
        }// end of run

    }//end of class
}