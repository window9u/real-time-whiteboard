package client.frame;

import client.DataManager;
import client.network.RequestManager;
import client.network.ResponseManager;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    private final RequestManager requestManager;
    private final DataManager dataManager;
    public final static int RESIZE_AREA = 10;
    public final static Color SELECTED_COLOR = Color.RED;
    private final static int FRAME_WIDTH = 1000;
    private final static int  FRAME_HEIGHT = 1000;


    public MyFrame(DataManager dataManager , RequestManager requestManager, ResponseManager responseManager) {
        this.requestManager = requestManager;
        this.dataManager = dataManager;
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init(responseManager);
        setVisible(true);
    }
    private void init(ResponseManager responseManager) {
        FrameManager frameManager = new FrameManager(this.dataManager, this.requestManager);
        responseManager.setFrameManager(frameManager);
        this.add(new MenuArea(frameManager), BorderLayout.NORTH);
        DrawArea drawArea= new DrawArea(frameManager);
        this.add(drawArea, BorderLayout.CENTER);
        frameManager.registerDrawArea(drawArea);
    }

}
