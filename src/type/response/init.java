package type.response;

import java.io.Serializable;

public class init extends Response implements Serializable {
    private final int CONNECTION_ID;
    public init(int CONNECTION_ID){
        this.CONNECTION_ID = CONNECTION_ID;
    }
    public int getCONNECTION_ID(){
        return CONNECTION_ID;
    }
    @Override
    public String toString() {
        return super.toString()+"init{" +
                "CONNECTION_ID=" + CONNECTION_ID +
                '}';
    }
}
