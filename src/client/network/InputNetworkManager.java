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
    private final BlockingQueue<Boolean> removeQueue;
    private final BlockingQueue<Boolean> selectQueue;


    public InputNetworkManager(PaintingManager pm, BlockingQueue<Painting> paintingQueue,
                               BlockingQueue<Boolean> removeQueue, BlockingQueue<Boolean> selectQueue) {
        this.pm = pm;
        this.paintingQueue = paintingQueue;
        this.removeQueue = removeQueue;
        this.selectQueue = selectQueue;
    }
    public void create(create response) {
        if(response.isReply())//if the object is created by my request
            paintingQueue.add(response.getObject());
        else
            pm.createObjectResponse(response.getObject());
    }
    public void remove(remove response) {
        if(response.isReply())//if the object is removed by my request
            removeQueue.add(true);
        pm.removeResponse(response.getId());
    }
    public void update(update response) {
        pm.updateResponse(response.getObject());
    }
    public void select(select response) {
        if(response.isReply())//if the object is selected by my request
            selectQueue.add(response.isReply());
        else if(response.isError())//if the object is selected by other user
            System.out.println(response.getErrorMessage());
        pm.selectResponse(response.getId());
    }
    public void unselect(unselect response) {
        pm.unselectResponse(response.getId());
    }

}
