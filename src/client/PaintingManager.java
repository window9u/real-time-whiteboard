package client;

import client.component.*;
import client.component.Rectangle;
import client.network.OutputNetworkManager;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

public class PaintingManager {
    private final HashMap<Integer, Painting> paintings;
    private int id = 0;
    private OutputNetworkManager out;

    public PaintingManager() {
        //if server sends paintings, use them
        paintings = new HashMap<>();
    }

    public void setOutputNetworkManager(OutputNetworkManager outputNetworkManager) {
        this.out = outputNetworkManager;
    }

    public Painting createRectangle(Point start) {
        Point end = new Point(start.x + 100, start.y + 100);
        Painting p = new Rectangle(start, end, id++);
        try {
            out.createObject(p);
            paintings.put(p.getId(), p);
            return p;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Painting createCircle(Point start) {
        Painting p = new Circle(start, 50, id++);
        try {
            out.createObject(p);
            paintings.put(p.getId(), p);
            return p;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Painting createTextBox(Point start) {
        Painting p = new TextBox(start, new Point(start.x + 140, start.y + 20), id++);
        try {
            out.createObject(p);
            paintings.put(p.getId(), p);
            return p;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Painting createLine(Point start) {
        Point end = new Point(start.x + 100, start.y);
        Painting p = new Line(start, end, id++);
        try {
            out.createObject(p);
            paintings.put(p.getId(), p);
            return p;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void removePaint(int id) {
        try {
            out.removeObject(id);
            paintings.remove(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Vector<Painting> getPaintings() {
        return new Vector<>(this.paintings.values());
    }

    public Painting clickPainting(Point p) {
        for (Painting painting : paintings.values()) {
            if (!painting.isSelected() && painting.contains(p)) {
                if (this.Select(painting)) {
                    return painting;
                }
            }
        }
        return null;
    }

    public void setColor(Painting p, Color color) {
        p.setColor(color);
        try {
            out.updateObject(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFillColor(Painting p, Color color) {
        p.setFillColor(color);
        try {
            out.updateObject(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStroke(Painting p, Stroke stroke) {
        p.setStroke(stroke);
        try {
            out.updateObject(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void unSelect(Painting focusedPainting) {
        if (focusedPainting == null)
            return;
        focusedPainting.unSelect();
        try {
            out.unselectObject(focusedPainting.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean Select(Painting focusedPainting) {
        focusedPainting.select();
        try {
            out.selectObject(focusedPainting.getId());
            return out.getResponse();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void movePainting(Painting focusedPainting, int dx, int dy) {
        focusedPainting.move(dx, dy);
        try {
            out.updateObject(focusedPainting);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resizePainting(Painting focusedPainting, int dx, int dy) {
        focusedPainting.resize(dx, dy);
        try {
            out.updateObject(focusedPainting);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addText(TextBox focusedPainting, String text) {
        focusedPainting.addText(text);
        try {
            out.updateObject(focusedPainting);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeText(TextBox focusedPainting) {
        focusedPainting.removeText();
        try {
            out.updateObject(focusedPainting);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void serverCreateObject(Painting p){
        paintings.put(p.getId(), p);
    }
    public void serverRemoveObject(int id){
        paintings.remove(id);
    }
    public void serverUpdateObject(Painting p){
        paintings.put(p.getId(), p);
    }
    public void serverSelectObject(int id){
        paintings.get(id).select();
    }
    public void serverUnselectObject(int id){
        paintings.get(id).unSelect();
    }
}
