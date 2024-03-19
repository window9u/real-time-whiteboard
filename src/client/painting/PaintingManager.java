package client.painting;

import java.awt.*;
import java.util.Vector;

public class PaintingManager {
    private final Vector<Painting> paintings;
    private int id = 0;
    public PaintingManager() {
        paintings = new Vector<Painting>();
    }

    public Painting createRectangle(Point start) {
        Point end = new Point(start.x + 100, start.y + 100);
        Painting p = new Rectangle(start, end,id++);
        paintings.add(p);
        return p;
    }
    public Painting createCircle(Point start) {
        Painting p = new Circle(start, 50, id++);
        paintings.add(p);
        return p;
    }
    public Painting createText(Point start) {
        Painting p = new Text(start, "Hello" , id++);
        paintings.add(p);
        return p;
    }
    public Painting createLine(Point start) {
        Point end = new Point(start.x, start.y + 100);
        Painting p = new Line(start, end,id++);
        paintings.add(p);
        return p;
    }

    public void remove(int id) {
        for (Painting p : paintings) {
            if (p.getId() == id) {
                paintings.remove(p);
                break;
            }
        }
    }
    public Vector<Painting> getPaintings() {
        return paintings;
    }
    public Painting clickPainting(Point p) {
        for (Painting painting : paintings) {
            if (painting.contains(p)) {
                painting.select();
                return painting;
            }
        }
        return null;
    }


    public void unSelect(Painting focusedPainting) {
        focusedPainting.unSelect();
    }
    public void Select(Painting focusedPainting) {
        focusedPainting.select();
    }

    public void movePainting(Painting focusedPainting, int dx, int dy) {
        focusedPainting.move(dx, dy);
    }

    public void resizePainting(Painting focusedPainting, int i, int i1) {
        focusedPainting.resize(i, i1);
    }
}
