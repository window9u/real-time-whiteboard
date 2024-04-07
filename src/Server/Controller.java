package Server;

import client.component.Painting;
import message.Status;
import message.request.*;
import message.request.create;
import message.request.remove;
import message.request.select;
import message.request.unselect;
import message.request.update;
import message.response.disconnect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

public class Controller implements Runnable {

    private final BlockingQueue<Request> requestQueue;
    private final HashMap<Integer, socketWriter> connections;
    private final HashMap<Integer, Painting> paintings;
    private static int OBJECT_ID = 0;

    public Controller(HashMap<Integer, socketWriter> connections, BlockingQueue<Request> requestQueue) {
        this.connections = connections;
        this.requestQueue = requestQueue;
        paintings = new HashMap<>();
    }

    public void removeConnection(int CONNECTION_ID) {
        String name =connections.get(CONNECTION_ID).getName();
        System.out.println(name+" disconnected");
        connections.remove(CONNECTION_ID);
        for (socketWriter out : connections.values()) {
            try {
                out.write(new disconnect(name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                Request req = requestQueue.take();
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

    public void initConnection(ObjectOutputStream out, ObjectInputStream in, int CONNECTION_ID) {
        String name = null;
        try {
            init init = (init) in.readObject();
            name = init.getName();
            System.out.println(name + " connected");
            sendResponseToAll(new message.response.connect(name), CONNECTION_ID);
            out.writeObject(new message.response.init(CONNECTION_ID));
            for (Painting painting : paintings.values()) {
                out.writeObject(new message.response.create(painting));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        connections.put(CONNECTION_ID, new socketWriter(out, CONNECTION_ID,name));
    }

    private void sendResponseToAll(message.response.Response res, int requestSocket_ID) {
        for (socketWriter out : connections.values()) {
            if (out.getCONNECTION_ID() == requestSocket_ID) {
                res.setStatus(Status.REPLY);
            }
            try {
                out.write(res);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                res.setStatus(Status.SERVER_SENT_RESPONSE);
            }
        }
    }

    private void processCreate(create req) {
        Painting painting = req.getObject();
        painting.setId(generateId());
        paintings.put(painting.getId(), painting);
        message.response.create res = new message.response.create(painting);
        sendResponseToAll(res, req.getCONNECTION_ID());
    }

    private void processRemove(remove req) {
        int id = req.getPainting_id();
        paintings.remove(id);
        message.response.remove res = new message.response.remove(id);
        sendResponseToAll(res, req.getCONNECTION_ID());
    }

    private void processUpdate(update req) {
        message.response.update res = new message.response.update(req.getObject());
        for (socketWriter out : connections.values()) {
            if (out.getCONNECTION_ID() != req.getCONNECTION_ID())
                try {
                    out.write(res);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    private void processSelect(select req) {
        int id = req.getPaintingId();
        message.response.select res = new message.response.select(id);
        if (paintings.get(id).isSelected()) {//if the object is already selected
            res.setStatus(Status.ERROR);
            res.setErrorMessage("Object is already selected");
            try {
                connections.get(req.getCONNECTION_ID()).write(res);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {//if the object is selected by my request
            paintings.get(id).select();
            sendResponseToAll(res, req.getCONNECTION_ID());
        }
    }

    private void processUnselect(unselect req) {
        int id = req.getPaintingId();
        message.response.unselect res = new message.response.unselect(id);
        if (!paintings.get(id).isSelected()) {//if the object is already unselected
            res.setStatus(Status.ERROR);
            res.setErrorMessage("Object is already unselected");
            try {
                connections.get(req.getCONNECTION_ID()).write(res);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {//if the object is unselected by my request
            paintings.get(id).unselect();
            sendResponseToAll(res, req.getCONNECTION_ID());
        }
    }

    private int generateId() {
        return OBJECT_ID++;
    }
}
