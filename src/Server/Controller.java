package Server;

import client.component.Painting;
import message.Message;

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
    private void processCreate(create req) {
        Painting painting = req.getObject();
        painting.setId(generateId());
        paintings.put(painting.getId(), painting);
        for (socketWriter out : outlist) {
            type.response.create res=new type.response.create(painting);
            if(out.getCONNECTION_ID() == req.getCONNECTION_ID()) {
                res.setStatus("ok");
            }
            out.write(res);
        }
    }

    private int generateId() {
        return OBJECT_ID++;
    }

}
