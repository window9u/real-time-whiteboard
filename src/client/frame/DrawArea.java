package client.frame;

import client.component.Painting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DrawArea extends JPanel {
    private static final int DRAWING_AREA_WIDTH = 800;
    private static final int DRAWING_AREA_HEIGHT = 800;
    private final FrameManager fm;


    public DrawArea(FrameManager frameManager) {
        this.fm = frameManager;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(DRAWING_AREA_WIDTH, DRAWING_AREA_HEIGHT));
        requestFocusInWindow();
        MouseAdapter eventHandler = getMouseEventHandler();
        addMouseListener(eventHandler); // For mouse click, press, etc.
        addMouseMotionListener(eventHandler); // For mouse drag, move, etc.
        addKeyListener(getKeyEventHandler()); // For keyboard input
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Painting painting : fm.getPaintings()) {
            painting.draw(g);
        }
    }


    protected MouseAdapter getMouseEventHandler() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                fm.mousePressDrawingArea(e.getPoint());
                requestFocusInWindow();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                fm.mouseReleaseDrawArea();
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                fm.mouseDraggedDrawArea(e.getPoint());
                repaint();
            }
        };

    }

    protected KeyAdapter getKeyEventHandler() {
        return new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                fm.keyTyped(e);
                repaint();
            }

            @Override
            public void keyPressed(KeyEvent e) {
                fm.keyPressed(e);
                repaint();
            }
        };
    }
}
