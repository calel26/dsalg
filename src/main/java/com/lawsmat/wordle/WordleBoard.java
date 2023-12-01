package com.lawsmat.wordle;

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
import java.awt.Font;

public class WordleBoard {
    private JFrame frame;

    public WordleBoard() {
        frame = new JFrame("WordleBoard");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(frame.getSize());
        frame.add(new InnerBoard(frame.getSize()));
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String... argv) {
        new WordleBoard();
    }

    public record Guess(char[] letters, LetterState[] s) {
        private boolean wordWorks(String word) {
            ArrayList<Character> needed = new ArrayList<>();
            ArrayList<Character> not = new ArrayList<>();
            for(int i = 0; i < letters.length; i++) {
                LetterState st = s[i];
                if(st.equals(LetterState.NOT_FOUND))
                    not.add(letters[i]);
                if(st.equals(LetterState.FOUND))
                    needed.add(letters[i]);
            }
            for(int i = 0; i < word.length(); i++) {
                LetterState st = s[i];
                char guess = letters[i];
                char checked = word.charAt(i);

                // if the letter MUST be `guess` and it's not, the word doesn't work
                if(st.equals(LetterState.EXACT) && (guess != checked)) return false;
                // the letter cannot be where it is yellow
                if(st.equals(LetterState.FOUND) && (guess == checked)) return false;
            }
            for(char ch : needed) {
                if(!word.contains(""+ch)) return false;
            }
            for(char ch : not) {
                if(word.contains(""+ch) && !needed.contains(ch)) return false;
            }

            return true;
        }
    }

    public enum LetterState {
        EXACT,
        FOUND,
        NOT_FOUND
    }

    public static class InnerBoard extends JPanel implements Runnable, MouseListener {
        private Thread animator;
        Dimension d;
        String str = "";
        int xPos = 0;
        int yPos = 0;
        String theWord = "";
        String optWords = "";
        private ArrayList<Guess> guesses = new ArrayList<>();
        private final LetterState[] wordState = new LetterState[5];

        private void resetColors() {
            Arrays.fill(wordState, LetterState.NOT_FOUND);
        }

        public InnerBoard(Dimension dimension) {
            resetColors();
            setSize(dimension);
            setPreferredSize(dimension);
            addMouseListener(this);
            addKeyListener(new TAdapter());
            setFocusable(true);
            d = getSize();

            //call your OptimalWords Class
            OptimalWords o = new OptimalWords(5);
            o.calculateFreqs(true);
            String[] tempArr = o.getOptimalWords();
            for (String t : tempArr) {
                optWords += t + " ";
            }


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

            g2.setColor(Color.white);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 24));
            g2.drawString("Type your guess and add colors using arrow keys:", 10, 30);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 22));
            //print char here instead
            int xSpot = 10;

            for (int i = 0; i < str.length(); i++) {
                g2.setColor(Color.gray);

                if (wordState[i].equals(LetterState.FOUND)) {
                    g2.setColor(Color.orange);
                }

                if (wordState[i].equals(LetterState.EXACT)) {
                    g2.setColor(Color.green);
                }
                g2.drawString("" + str.charAt(i), xSpot, 60);
                xSpot += 20;
            }


            g2.setColor(Color.white);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 24));
            g2.drawString("Optimal Words:", 10, 150);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 16));
            g2.drawString("" + optWords, 10, 170);
            g2.setColor(Color.red);
            g2.fillRect(10, 200, 150, 150);
            g2.setColor(Color.blue);
            g2.fillRect(200, 200, 150, 150);
            g2.setColor(Color.orange);
            g2.fillRect(400, 200, 150, 150);
            // full reset. resets guesses
            g2.setColor(Color.MAGENTA);
            g2.fillRect(600, 200, 150, 150);
        }

        public void resetWord() {
            str = "";
        }

        public void fullReset() {
            guesses = new ArrayList<>();
            resetWord();
            resetColors();
        }

        public void rotate() {
            var i = str.length() - 1;
            wordState[i] = switch(wordState[i]) {
                case EXACT -> wordState[i] = LetterState.NOT_FOUND;
                case FOUND -> LetterState.EXACT;
                case NOT_FOUND -> LetterState.FOUND;
            };
        }

        public void submit() {
            System.out.println("submit!");
            String str = this.str.toLowerCase();
            guesses.add(new Guess(str.toCharArray(), wordState.clone()));
            OptimalWords w = new OptimalWords(5, (word) -> {
                for(var guess : guesses) {
                    if(!guess.wordWorks(word)) return false;
                }
                return true;
            });

            w.calculateFreqs(guesses.size() <= 2);
            optWords = "";
            for(String word : w.getOptimalWords()) {
                optWords += word + " ";
            }
        }

        public void mousePressed(MouseEvent e) {
            xPos = e.getX();
            yPos = e.getY();
            //str = xPos + " " + yPos;
            if (xPos > 10 && xPos < 170 && yPos > 200 && yPos < 350) {
                //red clicked
                resetWord();
            }
            if (xPos > 200 && xPos < 350 && yPos > 200 && yPos < 350) {
                //red clicked
                submit();
            }

            if (xPos > 400 && xPos < 550 && yPos > 200 && yPos < 350) {
                //red clicked
                rotate();
            }

            if (xPos > 600 && xPos < 750 && yPos > 200 && yPos < 350) {
                //magenta clicked
                fullReset();
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

        private class TAdapter extends KeyAdapter {
            public void keyPressed(KeyEvent e) {
                if(str.length() >= 5) return;
                int key = e.getKeyCode();
                String c = Character.toString((char) key);
                str += c;
            }
        }

        public void run() {
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