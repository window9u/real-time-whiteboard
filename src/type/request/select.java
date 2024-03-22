package type.request;

public class select extends Request {
    private final int paintingId;
    public select(int paintingId){
        this.paintingId = paintingId;
    }
    public int getPaintingId(){
        return paintingId;
    }

}
