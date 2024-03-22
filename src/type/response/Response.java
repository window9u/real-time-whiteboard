package type.response;

public class Response {
    private String status;
    public void setStatus(String  status){
        this.status = status;
    }
    public boolean getStatus(){
        return status.equals("ok");
    }
}
