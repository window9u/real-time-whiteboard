package client;

import client.painting.PaintingManager;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    private DrawingArea drawingArea;
    private final PaintingManager paintingManager;
    public final static int RESIZE_AREA = 10;
    public final static Color SELECTED_COLOR = Color.RED;

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
        addMenuArea();
        getDrawingArea();
    }

    public void addMenuArea() {
        JPanel top = new JPanel();
        registerButtons(top);
        registerComboBoxes(top);
        // Add color, fill color, and line width combo boxes
        // Optionally, for better user experience, set renderers to display the colors and line widths visually
        this.add(top, BorderLayout.NORTH);
    }

    private void registerComboBoxes(JPanel top) {
        Color[] colors = {Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
        Float[] lineWidths = {1f, 2f, 3f, 4f, 5f};
        JComboBox<Color> colorComboBox = new JComboBox<>(colors);
        JComboBox<Color> fillColorComboBox = new JComboBox<>(colors);
        JComboBox<Float> lineWidthComboBox = new JComboBox<>(lineWidths);

        colorComboBox.addActionListener(e -> {
            Color selectedColor = (Color) colorComboBox.getSelectedItem();
            // Assuming you have a method to set the color of the selected Painting object
            if(drawingArea.getFocusedPainting() != null) {
                paintingManager.setColor(drawingArea.getFocusedPainting(), selectedColor);
            }
        });

        fillColorComboBox.addActionListener(e -> {
            Color selectedFillColor = (Color) fillColorComboBox.getSelectedItem();
            // Similarly, set the fill color of the selected Painting object
            if(drawingArea.getFocusedPainting() != null)
                paintingManager.setFillColor(drawingArea.getFocusedPainting(), selectedFillColor);
        });

        lineWidthComboBox.addActionListener(e -> {
            Float selectedLineWidth = (Float) lineWidthComboBox.getSelectedItem();
            // Update the stroke of the selected Painting object
            if(drawingArea.getFocusedPainting() != null)
                paintingManager.setStroke(drawingArea.getFocusedPainting(), new BasicStroke(selectedLineWidth));
        });
        top.add(colorComboBox);
        top.add(fillColorComboBox);
        top.add(lineWidthComboBox);

    }

    private void registerButtons(JPanel top) {
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
    }

    private void getDrawingArea() {
        DrawingArea drawingArea = new DrawingArea(this.paintingManager);
        this.drawingArea = drawingArea;
        this.add(drawingArea, BorderLayout.CENTER);
    }
}
