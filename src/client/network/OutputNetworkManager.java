package client.network;

import client.component.Painting;
import type.request.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingQueue;

public class OutputNetworkManager {
    private final ObjectOutputStream out;
    private final BlockingQueue<Painting> createResponse;
    private final BlockingQueue<Boolean> removeResponse;
    private final BlockingQueue<Boolean> selectResponse;
    private int CONNECTION_ID;

    public OutputNetworkManager(ObjectOutputStream out, BlockingQueue<Painting> createResponse,
                                BlockingQueue<Boolean> removeResponse, BlockingQueue<Boolean> selectResponse) {
        this.out = out;
        this.createResponse = createResponse;
        this.removeResponse = removeResponse;
        this.selectResponse = selectResponse;
    }
    public void init(int CONNECTION_ID){
        this.CONNECTION_ID = CONNECTION_ID;
    }
    public Painting create(Painting object) throws IOException, InterruptedException {
        create req=new create(object);
        req.setCONNECTION_ID(CONNECTION_ID);
        //send to server
        out.writeObject(req);
        //wait for response
        return createResponse.take();
    }
    public void remove(int id) throws IOException, InterruptedException {
        remove req=new remove(id);
        req.setCONNECTION_ID(CONNECTION_ID);
        out.writeObject(req);
        removeResponse.take();
    }
    public void update(Painting object) throws IOException{
        update req=new update(object);
        req.setCONNECTION_ID(CONNECTION_ID);
        out.writeObject(req);
    }
    public boolean select(int id) throws IOException, InterruptedException {
        select req=new select(id);
        req.setCONNECTION_ID(CONNECTION_ID);
        out.writeObject(req);
        return selectResponse.take();
    }
    public void unselect(int id) throws IOException, InterruptedException{
        unselect req=new unselect(id);
        req.setCONNECTION_ID(CONNECTION_ID);
        out.writeObject(req);
    }

}
