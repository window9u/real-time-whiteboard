package client.network;

import client.component.Painting;
import message.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingQueue;

public class OutputNetworkManager {
    private final ObjectOutputStream out;
    private final BlockingQueue<Painting> createResponse;
    private final BlockingQueue<Boolean> removeResponse;
    private final BlockingQueue<Boolean> selectResponse;
    private final BlockingQueue<Boolean> unselectResponse;

    public OutputNetworkManager(ObjectOutputStream out, BlockingQueue<Painting> createResponse,
                                BlockingQueue<Boolean> removeResponse, BlockingQueue<Boolean> selectResponse,
                                BlockingQueue<Boolean> unselectResponse) {
        this.out = out;
        this.createResponse = createResponse;
        this.removeResponse = removeResponse;
        this.selectResponse = selectResponse;
        this.unselectResponse = unselectResponse;
    }
    public Painting createObject(Painting object) throws IOException, InterruptedException {
        out.writeObject(new create(object));
        return createResponse.take();
    }
    public void removeObject(int id) throws IOException, InterruptedException {
        out.writeObject(new remove(id));
        removeResponse.take();
    }
    public void updateObject(Painting object) throws IOException{
        out.writeObject(new update(object));
    }
    public boolean selectObject(int id) throws IOException, InterruptedException {
        out.writeObject(new select(id));
        return selectResponse.take();
    }
    public void unselectObject(int id) throws IOException{
        out.writeObject(new unselect(id));
    }
}
