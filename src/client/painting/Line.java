package client.painting;

import client.MyFrame;

import java.awt.*;

public class Line extends Painting{
    private Point start;
    private Point end;
    private boolean startSelected = false;
    private boolean endSelected = false;


    public Line(Point start, Point end, int id) {
        this.start = start;
        this.end = end;
        this.id = id;
    }
    @Override
    public boolean contains(Point p) {
        //check if the point is on the line
        double d1 = start.distance(p) + p.distance(end);
        double d2 = start.distance(end);
        return Math.abs(d1-d2) < 0.1;
    }

    @Override
    public void move(int dx, int dy) {
        this.start.translate(dx, dy);
        this.end.translate(dx, dy);
    }
    @Override
    public void resize(int dx, int dy){
        if(startSelected){
            start.translate(dx, dy);
        }else if(endSelected) {
            end.translate(dx, dy);
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
        g2d.drawLine(start.x, start.y, end.x, end.y);
        g2d.setColor(originalColor);
        g2d.setStroke(originalStroke);
    }
    @Override
    public boolean isClickResizeArea(Point p){
        if(start.distance(p) < MyFrame.RESIZE_AREA){
            startSelected = true;
            return true;
        }else if (end.distance(p) < MyFrame.RESIZE_AREA){
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
