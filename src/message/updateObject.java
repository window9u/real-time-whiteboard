package message;

import client.component.Painting;

public class updateObject implements Message{
    private final Painting object;
    public updateObject(Painting object){
        this.object = object;
    }
    public Painting getObject(){
        return object;
    }
}
