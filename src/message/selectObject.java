package message;

public class selectObject implements Message{
    private final int id;
    public selectObject(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
}
