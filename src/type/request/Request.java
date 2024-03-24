package type.request;

import java.io.Serializable;

public class Request implements Serializable {
    private int CONNECTION_ID;
    public int getCONNECTION_ID(){
        return CONNECTION_ID;
    }
    public void setCONNECTION_ID(int CONNECTION_ID){
        this.CONNECTION_ID = CONNECTION_ID;
    }
    @Override
    public String toString() {
        return "Request{" +
                "CONNECTION_ID=" + CONNECTION_ID +
                '}';
    }

}
