package client.frame;

import client.PaintingManager;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    private final PaintingManager paintingManager;
    public final static int RESIZE_AREA = 10;
    public final static Color SELECTED_COLOR = Color.RED;
    private final static int FRAME_WIDTH = 1000;
    private final static int  FRAME_HEIGHT = 1000;
    private FrameManager frameManager;


    public MyFrame(PaintingManager pm) {
        this.paintingManager = pm;
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addFrameManager();
        addMenuArea();
        addDrawingArea();
        setVisible(true);
    }
    public void addFrameManager() {
        this.frameManager = new FrameManager(this.paintingManager);
    }

    public void addMenuArea() {
        this.add(new MenuArea(frameManager), BorderLayout.NORTH);
    }

    private void addDrawingArea() {
        this.add(new DrawArea(frameManager), BorderLayout.CENTER);
    }

}
