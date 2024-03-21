package client;

import client.painting.Painting;
import client.painting.TextBox;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class FrameManager {
    private MyFrame.Action selectedAction = MyFrame.Action.NORMAL;
    private boolean isWriting = false;
    private Painting focusedPainting = null;
    private Point previousPoint = null;
    private final PaintingManager pm;
    public FrameManager(PaintingManager paintingManager) {
        this.pm = paintingManager;
    }

    private void setAction(MyFrame.Action action) {
        switch (action) {
            case DRAW_RECTANGLE:
            case DRAW_CIRCLE:
            case DRAW_TEXT:
            case DRAW_LINE:
            case NORMAL:
                if (focusedPainting != null) {
                    pm.unSelect(focusedPainting);
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
    // The following methods are called from DrawArea
    public void mousePressDrawingArea(Point p) {
        previousPoint = p;
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
                focusedPainting = pm.createRectangle(previousPoint);
                setAction(MyFrame.Action.FOCUS);
                pm.Select(focusedPainting);
                break;
            case DRAW_CIRCLE:
                focusedPainting = pm.createCircle(previousPoint);
                setAction(MyFrame.Action.FOCUS);
                pm.Select(focusedPainting);
                break;
            case DRAW_TEXT:
                focusedPainting = pm.createTextBox(previousPoint);
                setAction(MyFrame.Action.FOCUS);
                isWriting = true;
                pm.Select(focusedPainting);
                break;
            case DRAW_LINE:
                focusedPainting = pm.createLine(previousPoint);
                setAction(MyFrame.Action.FOCUS);
                pm.Select(focusedPainting);
                break;
            case NORMAL:
                focusedPainting = pm.clickPainting(previousPoint);
                if (focusedPainting != null) {
                    if(focusedPainting instanceof TextBox){
                        isWriting = true;
                    }
                    setAction(MyFrame.Action.FOCUS);
                    pm.Select(focusedPainting);
                }
                break;
            default:
                break;
        }
    }
    public void mouseReleaseDrawArea() {
        if (selectedAction == MyFrame.Action.MOVE || selectedAction == MyFrame.Action.RESIZE) {
            setAction(MyFrame.Action.FOCUS);
        }
    }
    public void mouseDraggedDrawArea(Point currentPoint) {
        if (selectedAction == MyFrame.Action.MOVE) {
            pm.movePainting(focusedPainting, currentPoint.x - previousPoint.x, currentPoint.y - previousPoint.y);
        } else if (selectedAction == MyFrame.Action.RESIZE) {
            pm.resizePainting(focusedPainting, currentPoint.x - previousPoint.x, currentPoint.y - previousPoint.y);
        }
        previousPoint = currentPoint;
    }
    public Vector<Painting> getPaintings(){
        return pm.getPaintings();
    }
    public void keyTyped(KeyEvent e) {
        if (isWriting) {
            TextBox focusedTextBox = (TextBox) focusedPainting;
            if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE){
                return;
            }
            pm.addText(focusedTextBox, String.valueOf(e.getKeyChar()));
        }
    }
    public void keyPressed(KeyEvent e){
        if (isWriting) {
            TextBox focusedTextBox = (TextBox) focusedPainting;
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                pm.removeText(focusedTextBox);
            }
        }
    }
    // The following methods are called from MenuArea
    public void rectangleButtonPressed() {
        setAction(MyFrame.Action.DRAW_RECTANGLE);
    }
    public void circleButtonPressed() {
        setAction(MyFrame.Action.DRAW_CIRCLE);
    }
    public void textButtonPressed() {
        setAction(MyFrame.Action.DRAW_TEXT);
    }
    public void lineButtonPressed() {
        setAction(MyFrame.Action.DRAW_LINE);
    }
    public void deleteButtonPressed() {
        if (focusedPainting != null) {
            pm.removePaint(focusedPainting.getId());
            this.setAction(MyFrame.Action.NORMAL);
        }
    }

    public void colorComboBoxSelected(String selectedColorString) {
        if (selectedColorString == null)
            return;
        Color selectedColor = getColorByString(selectedColorString);
        // Assuming you have a method to set the color of the selected Painting object
        if (focusedPainting != null) {
            pm.setColor(focusedPainting, selectedColor);
        }
    }
    public void fillColorComboBoxSelected(String selectedFillColorString) {
        if (selectedFillColorString == null)
            return;
        Color selectedFillColor = getColorByString(selectedFillColorString);
        // Assuming you have a method to set the fill color of the selected Painting object
        if (focusedPainting != null) {
            pm.setFillColor(focusedPainting, selectedFillColor);
        }
    }
    public void lineWidthComboBoxSelected(Float selectedLineWidth) {
        if (selectedLineWidth == null)
            return;
        // Update the stroke of the selected Painting object
        if (focusedPainting != null) {
            pm.setStroke(focusedPainting, new BasicStroke(selectedLineWidth));
        }
    }
    private Color getColorByString(String colorString) {
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