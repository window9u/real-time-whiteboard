package client;
import client.painting.Painting;
import client.painting.PaintingManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DrawingArea extends JPanel {

    private Point previousPoint = null;


    private Painting focusedPainting = null;
    private MyFrame.Action selectedAction = MyFrame.Action.NORMAL;
    private final PaintingManager paintingManager;

    public DrawingArea(PaintingManager pm) {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 800));
        this.paintingManager = pm;

        MouseAdapter eventHandler = getEventHandler();
        addMouseListener(eventHandler); // For mouse click, press, etc.
        addMouseMotionListener(eventHandler); // For mouse drag, move, etc.
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Painting painting : paintingManager.getPaintings()) {
            if(painting.isSelected()){
                g.setColor(Color.RED);
                painting.draw(g);
                g.setColor(Color.BLACK);
            }else{
                painting.draw(g);
            }
        }

    }
    public void setAction (MyFrame.Action action) {
        this.selectedAction = action;
        System.out.println("Action Changed: " + action);
    }
    protected MouseAdapter getEventHandler() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                previousPoint = e.getPoint();

                switch (selectedAction) {
                    case FOCUS://focus on the selected object
                        if(focusedPainting.isClickResizeArea(previousPoint)){
                            setAction(MyFrame.Action.RESIZE);
                        }else if(focusedPainting.isClickMoveArea(previousPoint)){
                            setAction(MyFrame.Action.MOVE);
                        } else{
                            paintingManager.unSelect(focusedPainting);
                            focusedPainting=null;
                            setAction(MyFrame.Action.NORMAL);
                        }
                        break;
                    case MOVE://move the selected object
                        break;
                    case RESIZE://resize the selected object
                        break;
                    case DRAW_RECTANGLE:
                        focusedPainting =paintingManager.createRectangle(previousPoint);
                        setAction(MyFrame.Action.FOCUS);
                        paintingManager.Select(focusedPainting);
                        break;
                    case DRAW_CIRCLE:
                        break;
                    case DRAW_TEXT:
                        break;
                    case DRAW_LINE:
                        break;
                    case NORMAL:
                        focusedPainting = paintingManager.clickPainting(previousPoint);
                        if(focusedPainting!=null){
                            setAction(MyFrame.Action.FOCUS);
                            paintingManager.Select(focusedPainting);
                        }
                        break;
                    default:
                        break;
                }
                repaint();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if(selectedAction== MyFrame.Action.MOVE || selectedAction== MyFrame.Action.RESIZE){
                    setAction(MyFrame.Action.FOCUS);
                }
                repaint();
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                Point currentPoint = e.getPoint();

                if(selectedAction== MyFrame.Action.MOVE){
                    paintingManager.movePainting(focusedPainting, currentPoint.x-previousPoint.x, currentPoint.y-previousPoint.y);
                }
                else if(selectedAction== MyFrame.Action.RESIZE){
                    paintingManager.resizePainting(focusedPainting, currentPoint.x-previousPoint.x, currentPoint.y-previousPoint.y);
                }
                previousPoint = currentPoint;
                repaint();
            }
        };

    }
    public Painting getFocusedPainting() {
        return focusedPainting;
    }

}
