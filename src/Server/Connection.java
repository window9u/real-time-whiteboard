package Server;


import type.request.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.BlockingQueue;

public class Connection implements Runnable {
    private final BlockingQueue<Request> requestQueue;
    private final ObjectInputStream in;
    public Connection(ObjectInputStream in, BlockingQueue<Request> requestQueue) {
        this.requestQueue = requestQueue;
        this.in = in;
    }
    @Override
    public void run() {
        while (true) {
            try {
                Request m= (Request) in.readObject();
                requestQueue.put(m);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                break;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
