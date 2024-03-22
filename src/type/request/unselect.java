package type.request;

public class unselect extends Request {
    private final int paintingId;
    private boolean owner;
    private boolean success;
    public unselect(int paintingId){
        this.paintingId = paintingId;
    }
    public int getPaintingId(){
        return paintingId;
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
