package client.component;

import client.frame.MyFrame;

import java.awt.*;

public class Line extends Painting {
    private boolean startSelected = false;
    private boolean endSelected = false;


    public Line(Point start, Point end, int id) {
        this.startPoint = start;
        this.endPoint = end;
        this.id = id;
    }
    @Override
    public boolean contains(Point p) {
        //check if the point is on the line
        double d1 = startPoint.distance(p) + p.distance(endPoint);
        double d2 = startPoint.distance(endPoint);
        return Math.abs(d1-d2) < 0.1;
    }

    @Override
    public void move(int dx, int dy) {
        this.startPoint.translate(dx, dy);
        this.endPoint.translate(dx, dy);
    }
    @Override
    public void resize(int dx, int dy){
        if(startSelected){
            startPoint.translate(dx, dy);
        }else if(endSelected) {
            endPoint.translate(dx, dy);
        }
    }
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Color originalColor = g2d.getColor();
        Stroke originalStroke = g2d.getStroke();
        if(stroke != null){
            g2d.setStroke(stroke);
        }
        if(color != null){
            g2d.setColor(color);
        }
        if (isSelected()){
            g2d.setColor(MyFrame.SELECTED_COLOR);
        }
        g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        g2d.setColor(originalColor);
        g2d.setStroke(originalStroke);
    }
    @Override
    public boolean isClickResizeArea(Point p){
        if(startPoint.distance(p) < MyFrame.RESIZE_AREA){
            startSelected = true;
            return true;
        }else if (endPoint.distance(p) < MyFrame.RESIZE_AREA){
            endSelected = true;
            return true;
        }else {
            startSelected = false;
            endSelected = false;
            return false;
        }
    }
    @Override
    public boolean isClickMoveArea(Point p){
        return contains(p);
    }
}
