package Server;


import message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.BlockingQueue;

public class Connection implements Runnable {
    private final BlockingQueue<Message> requestQueue;
    private final ObjectInputStream in;
    public Connection(ObjectInputStream in, BlockingQueue<Message> requestQueue) {
        this.requestQueue = requestQueue;
        this.in = in;
    }
    @Override
    public void run() {
        while (true) {
            try {
                Message m= (Message) in.readObject();
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
