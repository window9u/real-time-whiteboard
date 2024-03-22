package Server;

import java.io.ObjectOutputStream;

public class socketWriter {
    private final ObjectOutputStream out;
    private final int CONNECTION_ID;
    public socketWriter(ObjectOutputStream out, int CONNECTION_ID){
        this.out = out;
        this.CONNECTION_ID = CONNECTION_ID;
    }
    public void write(type.response.Response response){
        write(response);
    }
    public int getCONNECTION_ID(){
        return CONNECTION_ID;
    }
}
