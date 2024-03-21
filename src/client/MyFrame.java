package client;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    private DrawingArea drawingArea;
    private final PaintingManager paintingManager;
    public final static int RESIZE_AREA = 10;
    public final static Color SELECTED_COLOR = Color.RED;
    private final static int FRAME_WIDTH = 1000;
    private final static int  FRAME_HEIGHT = 1000;

    public enum Action {
        DRAW_RECTANGLE, DRAW_CIRCLE, DRAW_TEXT, DRAW_LINE, NORMAL, FOCUS, MOVE, RESIZE
    }

    public MyFrame(PaintingManager pm) {
        this.paintingManager = pm;
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getDrawingArea();
        addMenuArea();
        setVisible(true);
    }

    public void addMenuArea() {
        this.add(new MenuArea(this.drawingArea,this.paintingManager), BorderLayout.NORTH);
    }

    private void getDrawingArea() {
        DrawingArea drawingArea = new DrawingArea(this.paintingManager);
        this.drawingArea = drawingArea;
        this.add(drawingArea, BorderLayout.CENTER);
    }

}
