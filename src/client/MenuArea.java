package client;

import javax.swing.*;
import java.awt.*;

public class MenuArea extends JPanel {
    private final DrawingArea drawingArea;
    private final PaintingManager paintingManager;
    public MenuArea(DrawingArea drawingArea, PaintingManager paintingManager) {
        this.drawingArea = drawingArea;
        this.paintingManager = paintingManager;
        registerButtons();
        registerComboBoxes();
    }
    private void registerButtons() {
        JButton rectangle = new JButton("Rectangle");
        JButton circle = new JButton("Circle");
        JButton text = new JButton("Text");
        JButton line = new JButton("Line");
        JButton delete = new JButton("Delete");

        rectangle.addActionListener(e ->
                drawingArea.setAction(MyFrame.Action.DRAW_RECTANGLE));
        circle.addActionListener(e ->
                drawingArea.setAction(MyFrame.Action.DRAW_CIRCLE));
        text.addActionListener(e ->
                drawingArea.setAction(MyFrame.Action.DRAW_TEXT)
        );
        line.addActionListener(e -> drawingArea.setAction(MyFrame.Action.DRAW_LINE));

        delete.addActionListener(e -> {
            if (drawingArea.getFocusedPainting() != null) {
                paintingManager.removePaint(drawingArea.getFocusedPainting().getId());
                drawingArea.setAction(MyFrame.Action.NORMAL);
                drawingArea.repaint();

            }
        });
        this.add(rectangle);
        this.add(circle);
        this.add(text);
        this.add(line);
        this.add(delete);
    }
    private void registerComboBoxes() {
        String[] colors = {"BLACK", "RED", "GREEN", "BLUE", "YELLOW"};
        Float[] lineWidths = {1f, 2f, 3f, 4f, 5f};
        JComboBox<String> colorComboBox = new JComboBox<>(colors);
        JComboBox<String> fillColorComboBox = new JComboBox<>(colors);
        JComboBox<Float> lineWidthComboBox = new JComboBox<>(lineWidths);

        colorComboBox.addActionListener(e -> {
            String selectedColorString = (String) colorComboBox.getSelectedItem();
            if(selectedColorString == null)
                return;
            Color selectedColor=getColorByString(selectedColorString);
            // Assuming you have a method to set the color of the selected Painting object
            if (drawingArea.getFocusedPainting() != null) {
                paintingManager.setColor(drawingArea.getFocusedPainting(), selectedColor);
            }
        });

        fillColorComboBox.addActionListener(e -> {
            String selectedFillColorString = (String) fillColorComboBox.getSelectedItem();
            if(selectedFillColorString == null)
                return;
            Color selectedFillColor=getColorByString(selectedFillColorString);
            if (drawingArea.getFocusedPainting() != null)
                paintingManager.setFillColor(drawingArea.getFocusedPainting(), selectedFillColor);
        });

        lineWidthComboBox.addActionListener(e -> {
            Float selectedLineWidth = (Float) lineWidthComboBox.getSelectedItem();
            if(selectedLineWidth == null)
                return;
            // Update the stroke of the selected Painting object
            if (drawingArea.getFocusedPainting() != null)
                paintingManager.setStroke(drawingArea.getFocusedPainting(), new BasicStroke(selectedLineWidth));
        });
        this.add(colorComboBox);
        this.add(fillColorComboBox);
        this.add(lineWidthComboBox);

    }
    private Color getColorByString(String colorString){
        switch (colorString) {
            case "RED":
                return Color.RED;
            case "GREEN":
                return Color.GREEN;
            case "BLUE":
                return Color.BLUE;
            case "YELLOW":
                return Color.YELLOW;
            default:
                return Color.BLACK;
        }
    }



}
