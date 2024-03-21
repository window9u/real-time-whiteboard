package message;

public class unselectObject implements Message{
    private final int id;
    public unselectObject(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
}
