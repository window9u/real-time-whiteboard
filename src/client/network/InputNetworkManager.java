package client.network;

import client.PaintingManager;
import client.component.Painting;
import message.create;
import message.remove;
import message.update;
import message.select;
import message.unselect;

import java.util.concurrent.BlockingQueue;

public class InputNetworkManager {
    private final PaintingManager pm;
    private final BlockingQueue<Painting> paintingQueue;
    private final BlockingQueue<Boolean> removeResponse;
    private final BlockingQueue<Boolean> selectResponse;

    private final BlockingQueue<Boolean> unselectResponse;

    public InputNetworkManager(PaintingManager pm, BlockingQueue<Painting> paintingQueue,
                               BlockingQueue<Boolean> removeResponse, BlockingQueue<Boolean> selectResponse,
                               BlockingQueue<Boolean> unselectResponse) {
        this.pm = pm;
        this.paintingQueue = paintingQueue;
        this.removeResponse = removeResponse;
        this.selectResponse = selectResponse;
        this.unselectResponse = unselectResponse;
    }
    public void createObject(create response) {
        if(response.getOwner())//if the object is created by my request
            paintingQueue.add(response.getObject());
        else
            pm.serverCreateObject(response.getObject());
    }
    public void removeObject(remove response) {
        if(response.getOwner())//if the object is removed by my request
            removeResponse.add(true);
        pm.serverRemoveObject(response.getId());
    }
    public void updateObject(update response) {
        pm.serverUpdateObject(response.getObject());
    }
    public void selectObject(select response) {
        if(response.getOwner())//if the object is selected by my request
            selectResponse.add(response.getSuccess());
        pm.serverSelectObject(response.getId());
    }
    public void unselectObject(unselect response) {
        if(response.getOwner())//if the object is unselected by my request
            unselectResponse.add(response.getSuccess());
        pm.serverUnselectObject(response.getId());
    }

}
