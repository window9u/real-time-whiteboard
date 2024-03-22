package type.response;

public class unselect extends Response {
    private final int id;
    public unselect(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }

}
