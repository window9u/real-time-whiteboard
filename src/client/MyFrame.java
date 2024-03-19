package client;

import client.painting.PaintingManager;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    private DrawingArea drawingArea;
    private final PaintingManager paintingManager;
    public final static int RESIZE_AREA = 10;

    public enum Action {
        DRAW_RECTANGLE, DRAW_CIRCLE, DRAW_TEXT, DRAW_LINE, NORMAL, FOCUS, MOVE, RESIZE
    }

    public MyFrame(PaintingManager pm) {
        this.paintingManager = pm;

        this.setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        setVisible(true);
    }

    public void init() {
        //add top on the button component and center is the drawing area
        addButtonArea();
        getDrawingArea();
    }

    public void addButtonArea() {
        JPanel top = new JPanel();
        JButton rectangle = new JButton("Rectangle");
        JButton circle = new JButton("Circle");
        JButton text = new JButton("Text");
        JButton line = new JButton("Line");
        JButton delete = new JButton("Delete");

        rectangle.addActionListener(e -> {
            drawingArea.setAction(Action.DRAW_RECTANGLE);
        });
        circle.addActionListener(e -> {
            drawingArea.setAction(Action.DRAW_CIRCLE);
        });
        text.addActionListener(e -> {
            drawingArea.setAction(Action.DRAW_TEXT);
        });
        line.addActionListener(e -> {
            drawingArea.setAction(Action.DRAW_LINE);
        });

        delete.addActionListener(e -> {
            if (drawingArea.getFocusedPainting() != null) {
                paintingManager.remove(drawingArea.getFocusedPainting().getId());
                drawingArea.setAction(Action.NORMAL);
                drawingArea.repaint();

            }
        });
        top.add(rectangle);
        top.add(circle);
        top.add(text);
        top.add(line);
        top.add(delete);
        this.add(top, BorderLayout.NORTH);
    }

    private void getDrawingArea() {
        DrawingArea drawingArea = new DrawingArea(this.paintingManager);
        this.drawingArea = drawingArea;
        this.add(drawingArea, BorderLayout.CENTER);
    }
}
