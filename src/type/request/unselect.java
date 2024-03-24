package type.request;

public class unselect extends Request {
    private final int paintingId;
    public unselect(int paintingId){
        this.paintingId = paintingId;
    }
    public int getPaintingId(){
        return paintingId;
    }
    @Override
    public String toString() {
        return super.toString()+"unselect{" +
                "paintingId=" + paintingId +
                '}';
    }

}
