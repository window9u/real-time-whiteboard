package client;

import client.component.*;
import client.component.Rectangle;
import client.frame.DrawArea;
import client.network.OutputNetworkManager;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

public class PaintingManager {
    private final HashMap<Integer, Painting> paintings;
    private OutputNetworkManager out;

    private DrawArea drawArea;
    public PaintingManager() {
        //if server sends paintings, use them
        paintings = new HashMap<>();
    }
    public void registerDrawingArea(DrawArea drawArea) {
        this.drawArea = drawArea;
    }
    public void repaint() {
        if (drawArea != null) {
            drawArea.repaint();
        }
    }

    public void setOutputNetworkManager(OutputNetworkManager outputNetworkManager) {
        this.out = outputNetworkManager;
    }

    public Painting createRectangleRequest(Point start) {
        //pessimistic approach
        Point end = new Point(start.x + 100, start.y + 100);
        Painting p = new Rectangle(start, end);
        try {
            Painting created = out.create(p);
            paintings.put(created.getId(), created);
            return created;
        } catch (IOException | InterruptedException e ) {
            e.printStackTrace();
            return null;
        }
    }

    public Painting createCircleRequest(Point start) {
        //pessimistic approach
        Painting p = new Circle(start, 50);
        try {
            Painting created = out.create(p);
            paintings.put(created.getId(), created);
            return created;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Painting createTextBoxRequest(Point start) {
        //pessimistic approach
        Painting p = new TextBox(start, new Point(start.x + 140, start.y + 20));
        try {
            Painting created = out.create(p);
            paintings.put(created.getId(), created);
            return created;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Painting createLineRequest(Point start) {
        //pessimistic approach
        Point end = new Point(start.x + 100, start.y);
        Painting p = new Line(start, end);
        try {
            Painting created = out.create(p);
            paintings.put(created.getId(), created);
            return created;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void RemoveRequest(int id) {
        //pessimistic approach
        try {
            out.remove(id);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Vector<Painting> getPaintings() {
        return new Vector<>(this.paintings.values());
    }

    public Painting clickPainting(Point p) {
        //pessimistic approach
        for (Painting painting : paintings.values()) {
            if (!painting.isSelected() && painting.contains(p)) {
                try {
                    if (out.select(painting.getId())) {
                        return painting;
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void unSelectRequest(Painting focusedPainting) {
        //pessimistic approach
        try {
            out.unselect(focusedPainting.getId());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void setColorRequest(Painting p, Color color) {
        //optimistic approach
        p.setColor(color);
        try {
            out.update(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFillColorRequest(Painting p, Color color) {
        //optimistic approach
        p.setFillColor(color);
        try {
            out.update(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStrokeRequest(Painting p, Stroke stroke) {
        //optimistic approach
        p.setStroke(stroke);
        try {
            out.update(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveRequest(Painting focusedPainting, int dx, int dy) {
        focusedPainting.move(dx, dy);
        try {
            out.update(focusedPainting);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resizeRequest(Painting focusedPainting, int dx, int dy) {
        focusedPainting.resize(dx, dy);
        try {
            out.update(focusedPainting);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTextRequest(TextBox focusedPainting, String text) {
        focusedPainting.addText(text);
        try {
            out.update(focusedPainting);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeTextRequest(TextBox focusedPainting) {
        focusedPainting.removeText();
        try {
            out.update(focusedPainting);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createObjectResponse(Painting p){
        paintings.put(p.getId(), p);
        repaint();
    }
    public void removeResponse(int id){
        paintings.remove(id);
        repaint();
    }
    public void updateResponse(Painting p){
        paintings.put(p.getId(), p);
        repaint();
    }
    public void selectResponse(int id){
        paintings.get(id).select();
        repaint();
    }
    public void unselectResponse(int id){
        paintings.get(id).unselect();
        repaint();
    }
}
