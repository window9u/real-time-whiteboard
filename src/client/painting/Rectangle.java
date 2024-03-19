package client.painting;

import java.awt.*;

public class Rectangle extends Painting{

    public Rectangle(Point start, Point end, int id) {
        this.startPoint = start;
        this.endPoint = end;
        this.id = id;
    }
    @Override
    public boolean contains(Point p) {
        return p.x >= startPoint.x && p.x <= endPoint.x && p.y >= startPoint.y && p.y <= endPoint.y;
    }
    @Override
    public void move(int dx, int dy) {
        startPoint.translate(dx, dy);
        endPoint.translate(dx, dy);
    }
    @Override
    public void resize(int dx, int dy){
        endPoint.translate(dx, dy);
        if (endPoint.x < startPoint.x) {
            int temp = startPoint.x;
            startPoint.x = endPoint.x;
            endPoint.x = temp;
        }
        if (endPoint.y < startPoint.y) {
            int temp = startPoint.y;
            startPoint.y = endPoint.y;
            endPoint.y = temp;
        }
    }
    @Override
    public void draw(Graphics g) {
        g.drawRect(startPoint.x, startPoint.y, endPoint.x - startPoint.x, endPoint.y - startPoint.y);
    }
    @Override
    public boolean isClickResizeArea(Point p){
        //check if the point is in the resize area(edge of rectangle)
        return this.contains(p) && !this.isClickMoveArea(p);
    }
    @Override
    public boolean isClickMoveArea(Point p){
        //check if the point is in the move area(center of rectangle)
        return p.x >= startPoint.x + 5 && p.x <= endPoint.x - 5 && p.y >= startPoint.y + 5 && p.y <= endPoint.y - 5;
    }

}
