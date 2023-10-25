package com.lawsmat.huff;

import com.lawsmat.baloneysearch.Bs;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class GUI {
    private JFrame frame;

    public GUI() {
        frame = new JFrame("Lawsmar® DeHuffman Inator-9000");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(frame.getSize());
        frame.add(new InnerCompress(frame.getSize()));
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String... argv) {
        new GUI();
    }

    public static class InnerCompress extends JPanel implements Runnable, MouseListener  {
        private Thread animator;
        String treeString = "e:00,t:010,s:0110,l:0111,k:100,a:101,c:110,i:11100,h:11101,o:1111";
        String toDecode = "10010101000011111100100000110110111011111110111101111010100011010110000";
        Dimension d;
        String s = "llanfairpwllgwyngythgogerachwyrndrobwllllantysiliogogogoch is a long name";
        String coded = "???";
        Bs<Main.CharFreq> tree;
        Rectangle button1 = new Rectangle(50, 50, 100, 50);
        Rectangle button2 = new Rectangle(50, 120, 100, 50);
        Rectangle button3 = new Rectangle(50, 190, 130, 50);
        Image myImage;
        String nName = "";
        int prev = 0;

        public InnerCompress (Dimension dimension) {
            setSize(dimension);
            setPreferredSize(dimension);
            addMouseListener(this);
            setFocusable(true);
            d = getSize();
            // Load the image using ImageIcon
            myImage = new ImageIcon("assets/huffman.png").getImage();  // Replace "path_to_image.jpg" with your image path


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
            g2.fillRect(0, 0,(int)d.getWidth() , (int)d.getHeight());

            //make three buttons
            g2.setColor(Color.WHITE);
            g2.fillRect(button1.x, button1.y, button1.width, button1.height);
            g2.fillRect(button2.x, button2.y, button2.width, button2.height);
            g2.fillRect(button3.x, button3.y, button3.width, button3.height);

            g2.setColor(Color.BLACK);
            g2.drawString("Encode", button1.x + 10, button1.y + 30);
            g2.drawString("Decode", button2.x + 10, button2.y + 30);
            g2.drawString("Decode via Tree", button3.x + 10, button3.y + 30);

            //String
            g2.setColor(Color.WHITE);
            g2.drawString("String: " + s, 50, 300);
            g2.setColor(Color.BLUE);
            g2.drawString("Laws", 10, 20);
            g2.setColor(Color.RED);
            g2.drawString("mar", 40, 20);
            g2.setColor(Color.WHITE);
            //Coded String
            g2.drawString("Encoded: " + coded, 50, 350);

            g2.drawImage(myImage, 250, 30, 400, 200, this);
        }

        public void mousePressed(MouseEvent e) {
            if (button1.contains(e.getPoint())) {
                Main.EncodeResult r = Main.encode(s);
                coded = r.bin();
                tree = r.tree();
                StringBuilder output = new StringBuilder();
                for(Map.Entry<Character, String> entry : r.map().entrySet()) {
                    output.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
                }
                System.out.println("output tree: " + output);
            } else if (button2.contains(e.getPoint())) {
                HashMap<Character, String> m = new HashMap<>();
                String[] s = treeString.split(",");
                for(String a : s) {
                    String[] kv = a.split(":");
                    m.put(kv[0].charAt(0), kv[1]);
                }
                this.s = Main.decodeWithHashMap(toDecode, m);
            } else if (button3.contains(e.getPoint())) {
                System.out.println("Button3 clicked");
                this.s = Main.decode(coded, tree);
            }
        }
        public void mouseReleased(MouseEvent e) {
        }
        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
        }

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