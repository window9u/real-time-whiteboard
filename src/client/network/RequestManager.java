package client.network;

import client.component.Painting;
import client.component.Rectangle;
import client.component.TextBox;
import type.request.*;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class RequestManager {
    private final ObjectOutputStream out;
    private int CONNECTION_ID;

    public RequestManager(ObjectOutputStream out) {
        this.out = out;
    }

    public void init(int CONNECTION_ID) {
        this.CONNECTION_ID = CONNECTION_ID;
    }

    public void createRectangle(Point start) {
        Point end = new Point(start.x + 100, start.y + 100);
        Painting p = new Rectangle(start, end);
        create req = new create(p);
        req.setCONNECTION_ID(CONNECTION_ID);
        try {
            out.writeObject(req);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createCircle(Point start) {
        Painting p = new client.component.Circle(start, 50);
        create req = new create(p);
        req.setCONNECTION_ID(CONNECTION_ID);
        try {
            out.writeObject(req);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createText(Point start) {
        Painting p = new client.component.TextBox(start, new Point(start.x + 140, start.y + 20));
        create req = new create(p);
        req.setCONNECTION_ID(CONNECTION_ID);
        try {
            out.writeObject(req);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void createLine(Point start) {
        Point end = new Point(start.x + 100, start.y);
        Painting p = new client.component.Line(start, end);
        create req = new create(p);
        req.setCONNECTION_ID(CONNECTION_ID);
        try {
            out.writeObject(req);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void remove(int id) {
        remove req = new remove(id);
        req.setCONNECTION_ID(CONNECTION_ID);
        try {
            out.writeObject(req);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void update(Painting object) {
        update req = new update(object);
        req.setCONNECTION_ID(CONNECTION_ID);
        try {
            out.reset();
            out.writeObject(req);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void select(Point p, Vector<Painting> paintings) {
        for (Painting painting : paintings) {
            if (!painting.isSelected() && painting.contains(p)) {
                try {
                    select req = new select(painting.getId());
                    req.setCONNECTION_ID(CONNECTION_ID);
                    out.writeObject(req);
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void free(Painting focusedPainting) {
        int id = focusedPainting.getId();
        unselect req = new unselect(id);
        req.setCONNECTION_ID(CONNECTION_ID);
        try {
            out.writeObject(req);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setColor(Painting p, Color color) {
        //optimistic approach
        p.setColor(color);
        update(p);
    }

    public void setFillColor(Painting p, Color color) {
        //optimistic approach
        p.setFillColor(color);
        update(p);
    }

    public void setStroke(Painting p, Stroke stroke) {
        //optimistic approach
        p.setStroke(stroke);
        update(p);
    }

    public void move(Painting focusedPainting, int dx, int dy) {
        focusedPainting.move(dx, dy);
        update(focusedPainting);
    }

    public void resize(Painting focusedPainting, int dx, int dy) {
        focusedPainting.resize(dx, dy);
        update(focusedPainting);
    }

    public void appendText(TextBox focusedPainting, String text) {
        focusedPainting.addText(text);
        update(focusedPainting);
    }

    public void removeText(TextBox focusedPainting) {
        focusedPainting.removeText();
        update(focusedPainting);
    }

}
