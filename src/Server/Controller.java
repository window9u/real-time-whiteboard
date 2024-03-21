package Server;

import client.component.Painting;
import message.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Controller implements Runnable {

    private final BlockingQueue<Message> requestQueue;
    private final List<ObjectOutputStream> outlist;
    private final HashMap<Integer, Painting> paintings;
    private static int OBJECT_ID = 0;

    public Controller(List<ObjectOutputStream> outlist, BlockingQueue<Message> messageQueue) {
        this.outlist = outlist;
        this.requestQueue = messageQueue;
        paintings = new HashMap<>();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message response = processRequest(requestQueue.take());
                for (ObjectOutputStream out : outlist) {
                    out.writeObject(response);
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private Message processRequest(Message request) {
        if (request instanceof message.createObject) {
            //createObject
            Painting painting = ((message.createObject) request).getObject();
            painting.setId(generateId());
            paintings.put(painting.getId(), painting);
            return new message.createObject(painting);
        } else if (request instanceof message.removeObject) {
            //removeObject
            int id = ((message.removeObject) request).getId();
            paintings.remove(id);
            return new message.removeObject(id);
        } else if (request instanceof message.updateObject) {
            //updateObject
            Painting painting = ((message.updateObject) request).getObject();
            paintings.put(painting.getId(), painting);
            return new message.updateObject(painting);
        } else if (request instanceof message.selectObject) {
            //selectObject
            int id = ((message.selectObject) request).getId();
            if (paintings.get(id).isSelected()) {
                return new message.response(false);
            } else{
                paintings.get(id).select();
                return new message.selectObject(id);
            }
        } else if (request instanceof message.unselectObject) {
            return new message.unselectObject(((message.unselectObject) request).getId());
            //unselectObject
        } else {
            return new message.response(false);
        }
    }

    private int generateId() {
        return OBJECT_ID++;
    }

}
