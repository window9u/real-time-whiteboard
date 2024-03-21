package client;

import client.painting.*;
import client.painting.Rectangle;

import java.awt.*;
import java.util.Vector;

public class PaintingManager {
    private final Vector<Painting> paintings;
    private int id = 0;

    public PaintingManager(Vector<Painting> paintings) {
        //if server sends paintings, use them
        if (paintings != null)
            this.paintings = paintings;
        else
            this.paintings = new Vector<>();
    }

    public Painting createRectangle(Point start) {
        Point end = new Point(start.x + 100, start.y + 100);
        Painting p = new Rectangle(start, end, id++);
        //send to server
        //if server accepts, add to paintings
        //server will send back the id
        paintings.add(p);
        return p;
    }

    public Painting createCircle(Point start) {
        Painting p = new Circle(start, 50, id++);
        paintings.add(p);
        return p;
    }

    public Painting createTextBox(Point start) {
        Painting p = new TextBox(start, new Point(start.x + 140, start.y + 20), id++);
        paintings.add(p);
        return p;
    }

    public Painting createLine(Point start) {
        Point end = new Point(start.x + 100, start.y);
        Painting p = new Line(start, end, id++);
        paintings.add(p);
        return p;
    }

    public void removePaint(int id) {
        for (Painting p : paintings) {
            if (p.getId() == id) {
                //send to server
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

    public void setColor(Painting p, Color color) {
        p.setColor(color);
    }

    public void setFillColor(Painting p, Color color) {
        p.setFillColor(color);
    }

    public void setStroke(Painting p, Stroke stroke) {
        p.setStroke(stroke);
    }


    public void unSelect(Painting focusedPainting) {
        if (focusedPainting != null)
            focusedPainting.unSelect();
    }

    public void Select(Painting focusedPainting) {
        focusedPainting.select();
    }

    public void movePainting(Painting focusedPainting, int dx, int dy) {
        focusedPainting.move(dx, dy);
    }

    public void resizePainting(Painting focusedPainting, int dx, int dy) {
        focusedPainting.resize(dx, dy);
    }

    public void addText(TextBox focusedPainting, String text) {
        focusedPainting.addText(text);
    }

    public void removeText(TextBox focusedPainting) {
        focusedPainting.removeText();
    }

}
