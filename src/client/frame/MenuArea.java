package client.frame;

import javax.swing.*;

public class MenuArea extends JPanel {
    private final FrameManager fm;

    public MenuArea(FrameManager frameManager) {
        this.fm = frameManager;
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
                fm.rectangleButtonPressed());
        circle.addActionListener(e ->
                fm.circleButtonPressed());
        text.addActionListener(e ->
                fm.textButtonPressed());
        line.addActionListener(e ->
                fm.lineButtonPressed());
        delete.addActionListener(e -> {
            fm.deleteButtonPressed();
            repaint();
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
            fm.colorComboBoxSelected(selectedColorString);
        });

        fillColorComboBox.addActionListener(e -> {
            String selectedFillColorString = (String) fillColorComboBox.getSelectedItem();
            fm.fillColorComboBoxSelected(selectedFillColorString);
        });
        lineWidthComboBox.addActionListener(e -> {
            Float selectedLineWidth = (Float) lineWidthComboBox.getSelectedItem();
            fm.lineWidthComboBoxSelected(selectedLineWidth);
        });
        this.add(colorComboBox);
        this.add(fillColorComboBox);
        this.add(lineWidthComboBox);
    }
}
