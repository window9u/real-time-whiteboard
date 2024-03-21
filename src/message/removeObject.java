package message;

public class removeObject implements Message{
    private int id;
    public removeObject(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
}
