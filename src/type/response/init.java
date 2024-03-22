package type.response;

public class init extends Response {
    private int CONNECTION_ID;
    public init(int CONNECTION_ID){
        this.CONNECTION_ID = CONNECTION_ID;
    }
    public int getCONNECTION_ID(){
        return CONNECTION_ID;
    }
    public void setCONNECTION_ID(int CONNECTION_ID){
        this.CONNECTION_ID = CONNECTION_ID;
    }
}
