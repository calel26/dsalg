package com.lawsmat.huff;

import com.lawsmat.baloneysearch.Bs;
import com.lawsmat.baloneysearch.BsNode;

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

public class Image {
    private record PixelFreq(String color, int freq) implements Comparable<PixelFreq> {
        @Override
        public int compareTo(PixelFreq o) {
            return freq - o.freq;
        }
    }
    private JFrame frame;

    private static void outputHuffman(HashMap<String, String> results, BsNode<PixelFreq> node, String s) {
        if(node.getLeft() != null) {
            outputHuffman(results, node.getLeft(), s + "0");
        }
        if (node.getRight() != null) {
            outputHuffman(results, node.getRight(), s + "1");
        } else if(node.getLeft() == null && node.getRight() == null) {
            results.put(s, node.get().color());
        }
    }


    public Image() {
        frame = new JFrame("Huffman De-Imageifier Inator-99000®");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(frame.getSize());
        frame.add(new InnerBoard(frame.getSize()));
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String... argv) {
        new Image();
    }

    public static class InnerBoard extends JPanel implements Runnable, MouseListener  {
        private Thread animator;
        Dimension d;
//         private final static String encodedImage = "000000111111111100000000000011111111111111111100000001100110011010100111110000000000110100110101010011111010100000011010011001101010100111110101000000110101010100111101111011110111100000000101010101010000000000011110101111010111100000011111101011110101111110011111111010010010010111111111010110100111001001001110010111010101010010010010010010010101010:y-2:b-7:c-9:d-18:w-33:g-37:r-38";
        private final static String encodedImage = "001100110011111111111001100111111111111001111111110001111100011110000000001110000000001010111111111101010101111111100000100101001000011111101010011001101011111110101010101011111111010101011111111010101010101111111010101010101111:b-2:d-90:g-8:w-10:y-34";
        private final static int height = 12;
        private final static int width = 12;
        private Color brush = colors.get("w");
        private Color[] image = new Color[width * height];

        public final static HashMap<String, Color> colors;

        static {
            colors = new HashMap<>();
            /*
                w = white
                r = red
                b = black
                c = brown
                g = gold
                d = blue
                y = yellow
             */
            colors.put("w", Color.WHITE);
            colors.put("r", Color.RED);
            colors.put("b", Color.BLACK);
            colors.put("c", new Color(101,0,0));
            colors.put("g", Color.ORANGE);
            colors.put("d", Color.BLUE);
            colors.put("y", Color.YELLOW);
        }

        private void decodeImage() {
            String[] split = encodedImage.split(":");
            String binCode = split[0];
            HashMap<String, Integer> frequencies = new HashMap<>();
            for(int i = 1; i < split.length; i++) {
                String[] a = split[i].split("-");
                frequencies.put(a[0], Integer.valueOf(a[1]));
            }
            System.out.println("got freqs: " + frequencies);
            PriorityQueue<BsNode<PixelFreq>> q = new PriorityQueue<>();
            for(Map.Entry<String, Integer> e : frequencies.entrySet()) {
                q.add(new BsNode<>(new PixelFreq(e.getKey(), e.getValue())));
            }
            while(q.size() > 1) {
                BsNode<PixelFreq> l = q.remove();
                BsNode<PixelFreq> r = q.remove();

                BsNode<PixelFreq> parent = new BsNode<>(new PixelFreq(
                        null,
                        l.get().freq() + r.get().freq()
                ));
                parent.setLeft(l);
                parent.setRight(r);
                q.add(parent);
            }
            Bs<PixelFreq> tree = new Bs<>(q.remove());
            HashMap<String, String> revmap = new HashMap<>();
            outputHuffman(revmap, tree.getRoot(), "");
            System.out.println("revmap " + revmap);
            String current = "";
            int pix = 0;
            for(int i = 0; i < binCode.length(); i++) {
                current += binCode.charAt(i);
                if(revmap.containsKey(current)) {
                    if(pix >= image.length) break;
                    image[pix] = colors.get(revmap.get(current));
                    pix++;
                    current = "";
                }
            }
            System.out.println("done");
        }

        private void encode() {
            HashMap<String, Integer> freq = new HashMap<>();
            HashMap<Color, String> colStr = new HashMap<>();

            for(Map.Entry<String, Color> e : colors.entrySet()) {
                colStr.put(e.getValue(), e.getKey());
            }

            for(int i = 0; i < image.length; i++) {
                Color col = image[i];
                String c = colStr.get(col);
                if(freq.containsKey(c)) {
                    freq.put(c, freq.get(c) + 1);
                } else {
                    freq.put(c, 1);
                }
            }
            System.out.println(freq);
            PriorityQueue<BsNode<PixelFreq>> q = new PriorityQueue<>();
            for(Map.Entry<String, Integer> e : freq.entrySet()) {
                q.add(new BsNode<>(new PixelFreq(e.getKey(), e.getValue())));
            }
            while(q.size() > 1) {
                BsNode<PixelFreq> l = q.remove();
                BsNode<PixelFreq> r = q.remove();

                BsNode<PixelFreq> parent = new BsNode<>(new PixelFreq(
                        null,
                        l.get().freq() + r.get().freq()
                ));
                parent.setLeft(l);
                parent.setRight(r);
                q.add(parent);
            }
            Bs<PixelFreq> tree = new Bs<>(q.remove());
            HashMap<String, String> res = new HashMap<>();
            outputHuffman(res, tree.getRoot(), "");
            HashMap<String, String> results = new HashMap<>();
            for(Map.Entry<String, String> e : res.entrySet()) {
                results.put(e.getValue(), e.getKey());
            }
            System.out.println(results);
            StringBuilder result = new StringBuilder();
            for(int i = 0; i < image.length; i++) {
                Color c = image[i];
                result.append(results.get(colStr.get(c)));
            }
            for(Map.Entry<String, Integer> f : freq.entrySet()) {
                result.append(":").append(f.getKey()).append("-").append(f.getValue());
            }
            System.out.println(result);
        }

        public InnerBoard (Dimension dimension) {
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
            decodeImage();
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(Color.black);
            g2.fillRect(0, 0,(int)d.getWidth() , (int)d.getHeight());

            g2.setColor(Color.WHITE);
            g2.fillRect(245, 245, 20*width+10, 20*height+10);

            for(int x = 0; x < width; x++) {
                for(int y = 0; y < height; y++) {
                    Color pixel = image[(y*height) + x];
                    g2.setColor(pixel);
                    g2.fillRect(250+(x*20), 250+(y*20), 20, 20);
                }
            }
        }

        public void mousePressed(MouseEvent e) {
            int img_x = (e.getX() - 250) / 20;
            int img_y = (e.getY() - 250) / 20;
            if(img_x < 0 || img_x > width || img_y < 0 || img_y > height) return;

            image[(img_y*height)+img_x] = brush;
        }

        public void mouseReleased(MouseEvent e) {}

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}

        public void mouseClicked(MouseEvent e) {}

        private class TAdapter extends KeyAdapter {
            public void keyReleased(KeyEvent e) {
                int keyr = e.getKeyCode();

            }

            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                // String c = KeyEvent.getKeyText(e.getKeyCode());
                //  c = Character.toString((char) key);
                if(e.getKeyChar() == KeyEvent.VK_SPACE) {
                    encode();
                }
                if(e.getKeyChar() == KeyEvent.VK_ENTER) {
                    for(int x = 0; x < width; x++) {
                        for(int y = 0; y < height; y++) {
                            image[(x*width)+y] = brush;
                        }
                    }
                }
                Color newC = colors.get("" + e.getKeyChar());
                if(newC != null) {
                    brush = newC;
                }
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