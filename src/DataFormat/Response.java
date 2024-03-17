package DataFormat;

import java.io.Serializable;

public class Response implements Serializable {
    public String content;
    public int id;
    public TYPE type;
    public enum TYPE{
        BROADCAST, UNICAST,UNDEFINED
    }
    public Response(String content, int id,TYPE type){
        this.content=content;
        this.id=id;
        this.type=type;
    }
}
