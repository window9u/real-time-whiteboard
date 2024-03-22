package message;

import client.component.Painting;

public class create implements Request ,Response{

    private final Painting object;
    private boolean owner;
    public create(Painting object){
        this.object = object;
    }
    public Painting getObject(){
        return object;
    }
    public void setOwner(boolean owner){
        this.owner = owner;
    }
    public boolean getOwner(){
        return owner;
    }
}
