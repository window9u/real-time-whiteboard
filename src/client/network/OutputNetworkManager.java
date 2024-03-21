package client.network;

import client.component.Painting;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingQueue;

public class OutputNetworkManager {
    private final ObjectOutputStream out;
    private final BlockingQueue<Boolean> responseQueue;
    public OutputNetworkManager(ObjectOutputStream out, BlockingQueue<Boolean> responseQueue) {
        this.out = out;
        this.responseQueue = responseQueue;
    }
    public void createObject(Painting object) throws IOException{
        out.writeObject(new message.createObject(object));
    }
    public void removeObject(int id) throws IOException{
        out.writeObject(new message.removeObject(id));
    }
    public void updateObject(Painting object) throws IOException{
        out.writeObject(new message.updateObject(object));
    }
    public void selectObject(int id) throws IOException{
        out.writeObject(new message.selectObject(id));
    }
    public void unselectObject(int id) throws IOException{
        out.writeObject(new message.unselectObject(id));
    }
    public boolean getResponse() throws InterruptedException {
        return responseQueue.take();
    }
}
