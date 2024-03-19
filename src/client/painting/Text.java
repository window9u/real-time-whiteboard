package client.painting;

import java.awt.*;

public class Text extends Painting{
    private String text;

    public Text(Point start, String text, int id) {
        this.startPoint = start;
        this.text = text;
        this.id = id;
    }
    @Override
    public boolean contains(Point p) {
        return false;
    }

    @Override
    public void move(int dx, int dy) {

    }

    @Override
    public void resize(int dx, int dy) {

    }
}
