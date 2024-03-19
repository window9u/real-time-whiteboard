package client.painting;

import java.awt.*;

public class Painting {
    public Point startPoint;
    public Point endPoint;
    public boolean isSelected;
    protected int id;
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
    public boolean isClickResizeArea(Point p){//will be overridden by the subclasses
        return false;
    }
    public boolean isClickMoveArea(Point p){//will be overridden by the subclasses
        return false;
    }
}
