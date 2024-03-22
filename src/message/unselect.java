package message;

public class unselect implements Request, Response {
    private final int id;
    private boolean owner;
    private boolean success;
    public unselect(int id){
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
    public void setSuccess(boolean success){
        this.success = success;
    }
    public boolean getSuccess(){
        return success;
    }

}
