package type.response;

public class select extends Response {
    private final int id;
    public select(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }

}
