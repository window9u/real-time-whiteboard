package Server;


import type.request.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Connection implements Runnable {
    private final BlockingQueue<Request> requestQueue;
    private final Socket conn;
    public Connection(Socket conn, BlockingQueue<Request> requestQueue) throws IOException {
        this.conn=conn;
        this.requestQueue = requestQueue;
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
