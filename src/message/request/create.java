package message.request;

import client.component.Painting;

public class create extends Request {

    private final Painting object;

    public create(Painting object){
        this.object = object;
    }
    public Painting getObject(){
        return object;
    }
    @Override
    public String toString() {
        return super.toString()+"create{" +
                "object=" + object +
                '}';
    }
}
