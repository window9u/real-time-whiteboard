package type.response;

public class remove extends Response {
    private int id;
    public remove(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
}
