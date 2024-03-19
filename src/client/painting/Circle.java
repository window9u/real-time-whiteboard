package client.painting;

import client.MyFrame;

import java.awt.Point;

public class Circle extends Painting{
    private int radius;

    public Circle(Point center, int radius, int id) {
        this.startPoint = center;
        this.radius = radius;
        this.id = id;
    }

    @Override
    public boolean contains(Point p) {
        double distance = this.startPoint.distance(p);
        return distance <= radius;
    }

    @Override
    public void move(int dx, int dy) {
        this.startPoint.translate(dx, dy);
    }
    @Override
    public void resize(int dx, int dy){
        if(Math.abs(dx) > Math.abs(dy)){
            this.radius += dx;
        }else{
            this.radius += dy;
        }
    }
    @Override
    public void draw(java.awt.Graphics g) {
        g.drawOval(startPoint.x - radius, startPoint.y - radius, radius * 2, radius * 2);
    }
    @Override
    public boolean isClickResizeArea(Point p){
        return contains(p) && p.distance(startPoint) > radius - MyFrame.RESIZE_AREA && p.distance(startPoint) < radius + MyFrame.RESIZE_AREA;
    }
    @Override
    public boolean isClickMoveArea(Point p){
        return contains(p) && p.distance(startPoint) <= radius - MyFrame.RESIZE_AREA;
    }

}
