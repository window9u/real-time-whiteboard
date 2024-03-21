package client;

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
        getDrawingArea();
        addMenuArea();
    }

    public void addMenuArea() {
        // Add color, fill color, and line width combo boxes
        // Optionally, for better user experience, set renderers to display the colors and line widths visually
        this.add(new MenuArea(this.drawingArea,this.paintingManager), BorderLayout.NORTH);
    }

    private void getDrawingArea() {
        DrawingArea drawingArea = new DrawingArea(this.paintingManager);
        this.drawingArea = drawingArea;
        this.add(drawingArea, BorderLayout.CENTER);
    }

}
