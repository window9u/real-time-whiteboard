package type.request;

import client.component.Painting;

public class update extends Request {
    private final Painting object;
    public update(Painting object){
        this.object = object;
    }
    public Painting getObject(){
        return object;
    }
}
