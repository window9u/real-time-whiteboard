package server;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class socketWriter {
    private final ObjectOutputStream out;
    private final int CONNECTION_ID;
    private int SELECTED_OBJECT_ID = -1;
    private final String name;
    public socketWriter(ObjectOutputStream out, int CONNECTION_ID, String name){
        this.out = out;
        this.name = name;
        this.CONNECTION_ID = CONNECTION_ID;
    }
    public void write(message.response.Response response) throws IOException {
        out.writeObject(response);
    }
    public int getCONNECTION_ID(){
        return CONNECTION_ID;
    }
    public String getName(){
        return name;
    }
    public void setSelectedObject(int id){
        SELECTED_OBJECT_ID = id;
    }
    public int getSelectedObject(){
        return SELECTED_OBJECT_ID;
    }
}
