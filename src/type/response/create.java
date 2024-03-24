package type.response;

import client.component.Painting;

public class create extends Response {

    private final Painting object;

    public create(Painting object){
        this.object = object;
    }
    public Painting getObject(){
        return object;
    }
    @Override
    final public String toString() {
        return super.toString()+"create{" +
                "object=" + object +
                '}';
    }

}
