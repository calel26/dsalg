package com.lawsmat.multitet;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class DoubleBufferedFrame extends Frame {
    private final Image bufferImage;
    private final Graphics bufferGraphics;

    public DoubleBufferedFrame(Dimension dims, String title) {
        super(title);
        setSize(dims);

        bufferImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        bufferGraphics = bufferImage.getGraphics();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });
    }

    protected void draw(Graphics2D g) { /* blank canvas */ }

    protected void drawBuffered(Graphics g) {
        var bg = (Graphics2D) bufferGraphics;
        draw(bg);

        // Copy the off-screen buffer to the screen
        g.drawImage(bufferImage, 0, 0, this);
    }

    @Override
    public void paint(Graphics g) {
        drawBuffered(g);
    }
}
