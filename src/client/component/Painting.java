package client.component;

import java.awt.*;
import java.io.Serializable;

public class Painting implements Serializable {
    protected Point startPoint;
    protected Point endPoint;
    private boolean isSelected=true;
    protected int id;
    protected Color color=null;
    protected Color fillColor=null;
    protected Float stroke=null;

    public void setColor(Color color) {
        this.color = color;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void setStroke(Float stroke) {
        this.stroke = stroke;
    }

    public boolean contains(Point p){
        return false;
    }
    public void move(int dx, int dy){}
    public void select(){
        this.isSelected=true;
    }
    public void unselect(){
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
    @Override
    public String toString() {
        return "Painting{" +
                ", isSelected=" + isSelected +
                ", id=" + id +
                ", x=" + startPoint.x +
                ", y=" + startPoint.y +
                '}';
    }
}
