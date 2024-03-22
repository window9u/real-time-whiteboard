package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class socketWriter {
    private final ObjectOutputStream out;
    private final int CONNECTION_ID;
    public socketWriter(ObjectOutputStream out, int CONNECTION_ID){
        this.out = out;
        this.CONNECTION_ID = CONNECTION_ID;
    }
    public void write(type.response.Response response) throws IOException {
        out.writeObject(response);
    }
    public int getCONNECTION_ID(){
        return CONNECTION_ID;
    }
}
