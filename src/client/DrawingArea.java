package client;

import client.painting.Painting;
import client.painting.TextBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DrawingArea extends JPanel {
    private static final int DRAWING_AREA_WIDTH = 800;
    private static final int DRAWING_AREA_HEIGHT = 800;
    private Point previousPoint = null;

    private Painting focusedPainting = null;
    private MyFrame.Action selectedAction = MyFrame.Action.NORMAL;
    private final PaintingManager paintingManager;
    private boolean isWriting = false;

    public DrawingArea(PaintingManager pm) {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(DRAWING_AREA_WIDTH, DRAWING_AREA_HEIGHT));
        this.paintingManager = pm;
        requestFocusInWindow();
        MouseAdapter eventHandler = getMouseEventHandler();
        addMouseListener(eventHandler); // For mouse click, press, etc.
        addMouseMotionListener(eventHandler); // For mouse drag, move, etc.
        addKeyListener(getKeyEventHandler()); // For keyboard input
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Painting painting : paintingManager.getPaintings()) {
                painting.draw(g);
        }
    }

    public void setAction(MyFrame.Action action) {
        switch (action) {
            case DRAW_RECTANGLE:
            case DRAW_CIRCLE:
            case DRAW_TEXT:
            case DRAW_LINE:
            case NORMAL:
                if (focusedPainting != null) {
                    paintingManager.unSelect(focusedPainting);
                    isWriting = false;
                    focusedPainting = null;
                }
                break;
            default:
                break;
        }
        this.selectedAction = action;
        System.out.println("Action Changed: " + action);
    }

    protected MouseAdapter getMouseEventHandler() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                previousPoint = e.getPoint();
                requestFocusInWindow();
                switch (selectedAction) {
                    case FOCUS://focus on the selected object
                        if (focusedPainting.isClickResizeArea(previousPoint)) {
                            setAction(MyFrame.Action.RESIZE);
                        } else if (focusedPainting.isClickMoveArea(previousPoint)) {
                            setAction(MyFrame.Action.MOVE);
                        } else {
                            setAction(MyFrame.Action.NORMAL);
                        }
                        break;
                    case DRAW_RECTANGLE:
                        focusedPainting = paintingManager.createRectangle(previousPoint);
                        setAction(MyFrame.Action.FOCUS);
                        paintingManager.Select(focusedPainting);
                        break;
                    case DRAW_CIRCLE:
                        focusedPainting = paintingManager.createCircle(previousPoint);
                        setAction(MyFrame.Action.FOCUS);
                        paintingManager.Select(focusedPainting);
                        break;
                    case DRAW_TEXT:
                        focusedPainting = paintingManager.createTextBox(previousPoint);
                        setAction(MyFrame.Action.FOCUS);
                        isWriting = true;
                        paintingManager.Select(focusedPainting);
                        break;
                    case DRAW_LINE:
                        focusedPainting = paintingManager.createLine(previousPoint);
                        setAction(MyFrame.Action.FOCUS);
                        paintingManager.Select(focusedPainting);
                        break;
                    case NORMAL:
                        focusedPainting = paintingManager.clickPainting(previousPoint);
                        if (focusedPainting != null) {
                            if(focusedPainting instanceof TextBox){
                                isWriting = true;
                            }
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
                if (selectedAction == MyFrame.Action.MOVE || selectedAction == MyFrame.Action.RESIZE) {
                    setAction(MyFrame.Action.FOCUS);
                }
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point currentPoint = e.getPoint();

                if (selectedAction == MyFrame.Action.MOVE) {
                    paintingManager.movePainting(focusedPainting, currentPoint.x - previousPoint.x, currentPoint.y - previousPoint.y);
                } else if (selectedAction == MyFrame.Action.RESIZE) {
                    paintingManager.resizePainting(focusedPainting, currentPoint.x - previousPoint.x, currentPoint.y - previousPoint.y);
                }
                previousPoint = currentPoint;
                repaint();
            }
        };

    }
    protected KeyAdapter getKeyEventHandler() {
        return new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (isWriting) {
                    TextBox focusedTextBox = (TextBox) focusedPainting;
                    if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE){
                        return;
                    }
                    paintingManager.addText(focusedTextBox, String.valueOf(e.getKeyChar()));
                    repaint();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (isWriting) {
                    TextBox focusedTextBox = (TextBox) focusedPainting;
                    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                        paintingManager.removeText(focusedTextBox);
                        repaint();
                    }
                }
            }
        };
    }
    public Painting getFocusedPainting() {
        return focusedPainting;
    }

}
