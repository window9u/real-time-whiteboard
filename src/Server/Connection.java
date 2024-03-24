package Server;


import type.request.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Connection implements Runnable {
    private final BlockingQueue<Request> requestQueue;
    private final Socket conn;
    private final Controller controller;
    public Connection(Socket conn, BlockingQueue<Request> requestQueue,Controller controller) throws IOException {
        this.conn=conn;
        this.requestQueue = requestQueue;
        this.controller = controller;
    }
    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(this.conn.getInputStream());
            while (true) {
                try {
                    Request m= (Request) in.readObject();
                    requestQueue.put(m);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    break;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                this.conn.close();
                controller.removeConnection(this.conn.hashCode());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
