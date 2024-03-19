package client.painting;

import java.awt.Point;

public class Circle extends Painting{
    private Point center;
    private int radius;

    public Circle(Point center, int radius, int id) {
        this.startPoint = center;
        this.radius = radius;
        this.id = id;
    }

    @Override
    public boolean contains(Point p) {
        double distance = center.distance(p);
        return distance <= radius;
    }

    @Override
    public void move(int dx, int dy) {
        center.translate(dx, dy);
    }
    @Override
    public void resize(int dx, int dy){

    }
}
