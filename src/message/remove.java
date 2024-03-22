package message;

public class remove implements Request ,Response{
    private int id;
    private boolean owner;
    public remove(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public void setOwner(boolean owner){
        this.owner = owner;
    }
    public boolean getOwner(){
        return owner;
    }
}
