package message;

import client.component.Painting;

public class createObject implements Message {

    private final Painting object;
    public createObject(Painting object){
        this.object = object;
    }
    public Painting getObject(){
        return object;
    }
}
