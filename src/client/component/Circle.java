package client.component;

import client.frame.MyFrame;

import java.awt.*;

public class Circle extends Painting {
    private int radius;

    public Circle(Point center, int radius) {
        this.startPoint = center;
        this.radius = radius;
    }

    @Override
    final public boolean contains(Point p) {
        double distance = this.startPoint.distance(p);
        return distance <= radius;
    }

    @Override
    final public void move(int dx, int dy) {
        this.startPoint.translate(dx, dy);
    }
    @Override
    final public void resize(int dx, int dy){
        if(Math.abs(dx) > Math.abs(dy)){
            this.radius += dx;
        }else{
            this.radius += dy;
        }
    }
    @Override
    final public void draw(java.awt.Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Color originalColor = g2d.getColor();
        Stroke originalStroke = g2d.getStroke();

        if(fillColor != null){
            g2d.setColor(fillColor);
            g2d.fillOval(startPoint.x - radius, startPoint.y - radius, radius * 2, radius * 2);
        }
        if(stroke != null){
            g2d.setStroke(stroke);
        }
        if(color != null){
            g2d.setColor(color);
        }
        if (isSelected()){
            g2d.setColor(MyFrame.SELECTED_COLOR);
        }
        g2d.drawOval(startPoint.x - radius, startPoint.y - radius, radius * 2, radius * 2);
        g2d.setColor(originalColor);
        g2d.setStroke(originalStroke);
    }
    @Override
    final public boolean isClickResizeArea(Point p){
        return contains(p) && p.distance(startPoint) > radius - MyFrame.RESIZE_AREA && p.distance(startPoint) < radius + MyFrame.RESIZE_AREA;
    }
    @Override
    final public boolean isClickMoveArea(Point p){
        return contains(p) && p.distance(startPoint) <= radius - MyFrame.RESIZE_AREA;
    }
    @Override
    final public String toString() {
        return super.toString()+"Circle{" +
                "radius=" + radius +
                '}';
    }
}
