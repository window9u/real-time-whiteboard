package message.response;

public class unselect extends Response {
    private final int id;
    public unselect(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }

    @Override
    public String toString() {
        return super.toString()+"unselect{" +
                "id=" + id +
                '}';
    }
}
