package type.response;

import client.component.Painting;

public class update extends Response {
    private final Painting object;
    public update(Painting object){
        this.object = object;
    }
    public Painting getObject(){
        return object;
    }
}
