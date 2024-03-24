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


    public MyFrame(PaintingManager pm) {
        this.paintingManager = pm;
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        setVisible(true);
    }
    private void init() {
        FrameManager frameManager = new FrameManager(this.paintingManager);
        this.add(new MenuArea(frameManager), BorderLayout.NORTH);
        DrawArea drawArea= new DrawArea(frameManager);
        this.add(drawArea, BorderLayout.CENTER);
        paintingManager.registerDrawingArea(drawArea);
    }

}
