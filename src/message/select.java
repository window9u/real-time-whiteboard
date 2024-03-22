package message;

public class select implements Request, Response {
    private final int id;
    private boolean owner;
    private boolean success;
    public select(int id){
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
