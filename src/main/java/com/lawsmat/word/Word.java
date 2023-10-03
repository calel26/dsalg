package com.lawsmat.word;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Word {
    private JFrame frame;

    public Word() {
        frame = new JFrame("Word™®");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(frame.getSize());
        frame.add(new WordProcessor(frame.getSize()));
        frame.pack();
        frame.setVisible(true);
    }
}