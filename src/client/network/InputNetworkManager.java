package client.network;

import client.PaintingManager;
import client.component.Painting;
import type.response.create;
import type.response.remove;
import type.response.update;
import type.response.select;
import type.response.unselect;

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
    public void create(create response) {
        if(response.getStatus())//if the object is created by my request
            paintingQueue.add(response.getObject());
        else
            pm.serverCreateObject(response.getObject());
    }
    public void remove(remove response) {
        if(response.getStatus())//if the object is removed by my request
            removeResponse.add(true);
        pm.serverRemoveObject(response.getId());
    }
    public void update(update response) {
        pm.serverUpdateObject(response.getObject());
    }
    public void select(select response) {
        if(response.getStatus())//if the object is selected by my request
            selectResponse.add(response.getStatus());
        pm.serverSelectObject(response.getId());
    }
    public void unselect(unselect response) {
        if(response.getStatus())//if the object is unselected by my request
            unselectResponse.add(response.getStatus());
        pm.serverUnselectObject(response.getId());
    }

}
