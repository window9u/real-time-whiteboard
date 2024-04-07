package message.response;

public class select extends Response {
    private final int id;
    public select(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    @Override
    public String toString() {
        return super.toString()+"select{" +
                "id=" + id +
                '}';
    }

}
