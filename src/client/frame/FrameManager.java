package client.frame;

import client.DataManager;
import client.network.RequestManager;
import client.component.Painting;
import client.component.TextBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class FrameManager {
    private Action curAction = Action.NORMAL;
    private boolean isWriting = false;
    private Painting focusedPainting = null;
    private Point previousPoint = null;
    private final DataManager dm;
    private final RequestManager request;
    private DrawArea drawArea;
    private JLabel printLabel;

    public void registerDrawArea(DrawArea drawArea) {
        this.drawArea = drawArea;
    }
    public void repaint() {
        Graphics g = drawArea.getGraphics();
        for (Painting painting : dm.getPaintings()) {
            painting.draw(g);
        }
        drawArea.repaint();
    }

    public enum Action {
        DRAW_RECTANGLE, DRAW_CIRCLE, DRAW_TEXT, DRAW_LINE, NORMAL, FOCUS, MOVE, RESIZE
    }

    public FrameManager(DataManager dm, RequestManager requestManager) {
        this.request = requestManager;
        this.dm = dm;
    }

    public void setAction(Action action) {
        this.curAction = action;
        System.out.println("Action Changed: " + action);
    }
    public void setFocusedPainting(Painting painting) {
        this.focusedPainting = painting;
    }

    // The following methods are called from DrawArea
    public void mousePressDrawingArea(Point p) {
        previousPoint = p;
        switch (curAction) {
            case FOCUS://focus on the selected object
                if (focusedPainting.isClickResizeArea(previousPoint)) {
                    setAction(Action.RESIZE);
                } else if (focusedPainting.isClickMoveArea(previousPoint)) {
                    setAction(Action.MOVE);
                } else {
                    request.free(focusedPainting);
                }
                break;
            case DRAW_RECTANGLE:
                request.createRectangle(previousPoint);
                break;
            case DRAW_CIRCLE:
                request.createCircle(previousPoint);
                break;
            case DRAW_TEXT:
                request.createText(previousPoint);
                isWriting = true;
                break;
            case DRAW_LINE:
                request.createLine(previousPoint);
                break;
            case NORMAL:
                request.select(p,dm.getPaintings());
                break;
            default:
                break;
        }
    }

    public void mouseReleaseDrawArea() {
        if (curAction == Action.MOVE || curAction == Action.RESIZE) {
            setAction(Action.FOCUS);
        }
    }

    public void mouseDraggedDrawArea(Point currentPoint) {
        if (curAction == Action.MOVE) {
            request.move(focusedPainting, currentPoint.x - previousPoint.x, currentPoint.y - previousPoint.y);
        } else if (curAction == Action.RESIZE) {
            request.resize(focusedPainting, currentPoint.x - previousPoint.x, currentPoint.y - previousPoint.y);
        }
        previousPoint = currentPoint;
    }

    public Vector<Painting> getPaintings() {
        return dm.getPaintings();
    }

    public void keyTyped(KeyEvent e) {
        if (isWriting) {
            TextBox focusedTextBox = (TextBox) focusedPainting;
            if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                return;
            }
            request.appendText(focusedTextBox, String.valueOf(e.getKeyChar()));
        }
    }

    public void keyPressed(KeyEvent e) {
        if (isWriting) {
            TextBox focusedTextBox = (TextBox) focusedPainting;
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                request.removeText(focusedTextBox);
            }
        }
    }
    // The following methods are called from MenuArea
    public void rectangleButtonPressed() {
        if(curAction == Action.FOCUS){
            request.free(focusedPainting);
        }
        setAction(Action.DRAW_RECTANGLE);
    }

    public void circleButtonPressed() {
        if(curAction == Action.FOCUS){
            request.free(focusedPainting);
        }
        setAction(Action.DRAW_CIRCLE);
    }

    public void textButtonPressed() {
        if(curAction == Action.FOCUS){
            request.free(focusedPainting);
        }
        setAction(Action.DRAW_TEXT);
    }

    public void lineButtonPressed() {
        if(curAction == Action.FOCUS){
            request.free(focusedPainting);
        }
        setAction(Action.DRAW_LINE);
    }

    public void deleteButtonPressed() {
        if (curAction == Action.FOCUS) {
            request.remove(focusedPainting.getId());
            focusedPainting = null;
            this.setAction(Action.NORMAL);
        }
    }

    public void colorComboBoxSelected(String selectedColorString) {
        if (selectedColorString == null)
            return;
        Color selectedColor = getColorByString(selectedColorString);
        // Assuming you have a method to set the color of the selected Painting object
        if (curAction == Action.FOCUS) {
            request.setColor(focusedPainting, selectedColor);
        }
    }

    public void fillColorComboBoxSelected(String selectedFillColorString) {
        if (selectedFillColorString == null)
            return;
        Color selectedFillColor = getColorByString(selectedFillColorString);
        // Assuming you have a method to set the fill color of the selected Painting object
        if (curAction == Action.FOCUS) {
            request.setFillColor(focusedPainting, selectedFillColor);
        }
    }

    public void lineWidthComboBoxSelected(Float selectedLineWidth) {
        if (selectedLineWidth == null)
            return;
        // Update the stroke of the selected Painting object
        if (curAction == Action.FOCUS) {
            request.setStroke(focusedPainting,  selectedLineWidth);
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
    public void setPrintLabel(JLabel printLabel){
        this.printLabel = printLabel;
    }

    public void print(String text){
        printLabel.setText(text);
    }
}