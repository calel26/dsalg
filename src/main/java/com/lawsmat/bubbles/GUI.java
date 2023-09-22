package com.lawsmat.bubbles;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.io.*;
import java.awt.Font;

public class GUI {
    private final JFrame frame;

    public GUI() {
        frame = new JFrame("bubbles");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setPreferredSize(frame.getSize()); caused the bottom part of the screen to not show
        frame.add(new BubblePainter(frame.getSize()));
        frame.pack();
        frame.setVisible(true);
    }
}