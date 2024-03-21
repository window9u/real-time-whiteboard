package client.component;

import java.awt.*;

public class Painting implements java.io.Serializable{
    protected Point startPoint;
    protected Point endPoint;
    private boolean isSelected;
    protected int id;
    protected Color color=null;
    protected Color fillColor=null;
    protected Stroke stroke=null;

    public void setColor(Color color) {
        this.color = color;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    public boolean contains(Point p){
        return false;
    }
    public void move(int dx, int dy){}
    public void select(){
        this.isSelected=true;
    }
    public void unSelect(){
        this.isSelected=false;
    }
    public boolean isSelected(){
        return this.isSelected;
    }
    public void resize(int dx, int dy){}//will be overridden by the subclasses
    public void draw(Graphics g){

    }//will be overridden by the subclasses
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id=id;
    }
    public boolean isClickResizeArea(Point p){//will be overridden by the subclasses
        return false;
    }
    public boolean isClickMoveArea(Point p){//will be overridden by the subclasses
        return false;
    }
}
