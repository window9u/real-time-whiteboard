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

    private final BlockingQueue<Boolean> unselectQueue;

    public InputNetworkManager(PaintingManager pm, BlockingQueue<Painting> paintingQueue,
                               BlockingQueue<Boolean> removeQueue, BlockingQueue<Boolean> selectQueue,
                               BlockingQueue<Boolean> unselectQueue) {
        this.pm = pm;
        this.paintingQueue = paintingQueue;
        this.removeQueue = removeQueue;
        this.selectQueue = selectQueue;
        this.unselectQueue = unselectQueue;
    }
    public void create(create response) {
        if(response.isReply())//if the object is created by my request
            paintingQueue.add(response.getObject());
        else
            pm.serverCreateObject(response.getObject());
    }
    public void remove(remove response) {
        if(response.isReply())//if the object is removed by my request
            removeQueue.add(true);
        pm.serverRemoveObject(response.getId());
    }
    public void update(update response) {
        pm.serverUpdateObject(response.getObject());
    }
    public void select(select response) {
        if(response.isReply())//if the object is selected by my request
            selectQueue.add(response.isReply());
        else if(response.isError())//if the object is selected by other user
            System.out.println(response.getErrorMessage());
        pm.serverSelectObject(response.getId());
    }
    public void unselect(unselect response) {
        if(response.isReply())//if the object is unselected by my request
            unselectQueue.add(response.isReply());
        else if(response.isError())//if the object is already selected by other user
            System.out.println(response.getErrorMessage());
        pm.serverUnselectObject(response.getId());
    }

}
