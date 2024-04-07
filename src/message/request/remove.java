package message.request;

public class remove extends Request {
    private final int painting_id;
    public remove(int painting_id){
        this.painting_id = painting_id;
    }
    public int getPainting_id(){
        return painting_id;
    }
    @Override
    public String toString() {
        return super.toString()+"remove{" +
                "painting_id=" + painting_id +
                '}';
    }
}
