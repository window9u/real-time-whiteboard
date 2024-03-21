package client.network;

import client.PaintingManager;
import client.component.Painting;

import java.util.concurrent.BlockingQueue;

public class InputNetworkManager {
    private final PaintingManager pm;
    private final BlockingQueue<Boolean> responseQueue;
    public InputNetworkManager(PaintingManager pm, BlockingQueue<Boolean> responseQueue) {
        this.pm = pm;
        this.responseQueue = responseQueue;
    }
    public void createObject(Painting object) {
        pm.serverCreateObject(object);
    }
    public void removeObject(int id) {
        pm.serverRemoveObject(id);
    }
    public void updateObject(Painting object) {
        pm.serverUpdateObject(object);
    }
    public void selectObject(int id) {
        pm.serverSelectObject(id);
    }
    public void unselectObject(int id) {
        pm.serverUnselectObject(id);
    }
    public void sendResponse(boolean response) {
        responseQueue.add(response);
    }

}
