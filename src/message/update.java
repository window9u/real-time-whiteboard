package message;

import client.component.Painting;

public class update implements Request, Response {
    private final Painting object;
    public update(Painting object){
        this.object = object;
    }
    public Painting getObject(){
        return object;
    }
}
