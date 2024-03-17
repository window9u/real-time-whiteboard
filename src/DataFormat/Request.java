package DataFormat;

import java.io.Serializable;

public class Request implements Serializable {
    public String content;
    public int id;
    public TYPE type;
    public enum TYPE{
        PREV,MESSAGE
    }
    public Request(String content, int id, TYPE type){
        this.content=content;
        this.id=id;
        this.type=type;
    }

}
