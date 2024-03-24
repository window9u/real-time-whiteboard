package Server;

import client.component.Painting;
import type.Status;
import type.request.*;
import type.request.create;
import type.request.remove;
import type.request.select;
import type.request.unselect;
import type.request.update;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Controller implements Runnable {

    private final BlockingQueue<Request> requestQueue;
    private final List<socketWriter> outlist;
    private final HashMap<Integer, Painting> paintings;
    private static int OBJECT_ID = 0;
    private static int CONNECTION_ID = 0;

    public Controller(List<socketWriter> outlist, BlockingQueue<Request> requestQueue) {
        this.outlist = outlist;
        this.requestQueue = requestQueue;
        paintings = new HashMap<>();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Request req=requestQueue.take();
                System.out.println(req);
                if (req instanceof create) {
                    processCreate(((create) req));
                } else if (req instanceof remove) {
                    processRemove(((remove) req));
                } else if (req instanceof update) {
                    processUpdate((update) req);
                } else if (req instanceof select) {
                    processSelect((select) req);
                } else if (req instanceof unselect) {
                    processUnselect((unselect) req);
                } else {
                    System.out.println("Unknown message type");
                    break;
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }
    public void initConnection(ObjectOutputStream out){
        try{
            out.writeObject(new type.response.init(CONNECTION_ID));
            for (Painting painting : paintings.values()) {
                out.writeObject(new type.response.create(painting));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        outlist.add(new socketWriter(out,CONNECTION_ID++));
    }
    private void sendResponseToAll(type.response.Response res, int requestSocket_ID) {
        for (socketWriter out : outlist) {
            if(out.getCONNECTION_ID() == requestSocket_ID) {
                res.setStatus(Status.REPLY);
            }
            try {
                out.write(res);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                res.setStatus(Status.SERVER_SENT_RESPONSE);
            }
        }
    }
    private void processCreate(create req) {
        Painting painting = req.getObject();
        painting.setId(generateId());
        paintings.put(painting.getId(), painting);
        type.response.create res=new type.response.create(painting);
        sendResponseToAll(res,req.getCONNECTION_ID());
    }

    private void processRemove(remove req) {
        int id = req.getPainting_id();
        paintings.remove(id);
        type.response.remove res=new type.response.remove(id);
        sendResponseToAll(res,req.getCONNECTION_ID());
    }
    private void processUpdate(update req) {
        type.response.update res=new type.response.update(req.getObject());
        for(socketWriter out : outlist){
            if(out.getCONNECTION_ID() != req.getCONNECTION_ID())
                try {
                    out.write(res);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
    private void processSelect(select req) {
        int id = req.getPaintingId();
        type.response.select res=new type.response.select(id);
        if(paintings.get(id).isSelected()) {//if the object is already selected
            res.setStatus(Status.ERROR);
            res.setErrorMessage("Object is already selected");
            try {
                outlist.get(req.getCONNECTION_ID()).write(res);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{//if the object is selected by my request
            paintings.get(id).select();
            sendResponseToAll(res,req.getCONNECTION_ID());
        }
    }
    private void processUnselect(unselect req) {
        int id = req.getPaintingId();
        type.response.unselect res=new type.response.unselect(id);
        if(!paintings.get(id).isSelected()) {//if the object is already unselected
            res.setStatus(Status.ERROR);
            res.setErrorMessage("Object is already unselected");
            try {
                outlist.get(req.getCONNECTION_ID()).write(res);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{//if the object is unselected by my request
            paintings.get(id).unselect();
            sendResponseToAll(res,req.getCONNECTION_ID());
        }
    }
    private int generateId() {
        return OBJECT_ID++;
    }
}
