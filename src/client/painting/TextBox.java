package client.painting;

import java.awt.*;

public class TextBox extends Rectangle{
    private String text = "";

    public TextBox(Point start,Point end, int id) {
        super(start, end, id);
    }
    public void addText(String text){
        this.text += text;
        if(this.text.length() % 20==0) {
            super.resize(0, 13);
            this.text += "\n";
        }
    }
    public void removeText(){
        if(text.length() > 0){
            text = text.substring(0, text.length() - 1);
        }
    }
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        Graphics2D g2d = (Graphics2D) g;
        String[] lines = text.split("\n");
        int currentY = startPoint.y+10;
        for (String line : lines) {
            g2d.drawString(line, startPoint.x, currentY);
            currentY += g2d.getFontMetrics().getHeight();
        }
    }
}
