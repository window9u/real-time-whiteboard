package type.request;

public class remove extends Request {
    private int painting_id;
    public remove(int painting_id){
        this.painting_id = painting_id;
    }
    public int getPainting_id(){
        return painting_id;
    }
}
