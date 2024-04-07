package Server;


import message.request.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Connection implements Runnable {
    private final BlockingQueue<Request> requestQueue;
    private final Socket conn;
    private final Controller controller;
    private final ObjectInputStream in;
    public Connection(Socket conn,ObjectInputStream in, BlockingQueue<Request> requestQueue,Controller controller) throws IOException {
        this.conn=conn;
        this.in = in;
        this.requestQueue = requestQueue;
        this.controller = controller;
    }
    @Override
    public void run() {
        try {
            while (true) {
                try {
                    Request m= (Request) in.readObject();
//                    System.out.println(m);
                    requestQueue.put(m);
                } catch (IOException | ClassNotFoundException e) {
                    if (e instanceof IOException){
                        System.out.println("Connection closed by client");
                    }else{
                        e.printStackTrace();
                    }
                    break;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } finally {
            try {
                this.conn.close();
                controller.removeConnection(this.conn.hashCode());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
