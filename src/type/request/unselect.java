package type.request;

public class unselect extends Request {
    private final int paintingId;
    public unselect(int paintingId){
        this.paintingId = paintingId;
    }
    public int getPaintingId(){
        return paintingId;
    }

}
