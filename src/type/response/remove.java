package type.response;

public class remove extends Response {
    private final int id;
    public remove(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    @Override
    public String toString() {
        return super.toString()+"remove{" +
                "id=" + id +
                '}';
    }
}
