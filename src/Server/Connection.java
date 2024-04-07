package Server;


import message.request.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Connection implements Runnable {
    private final BlockingQueue<Request> requestQueue;
    private final Socket conn;
    private final String name;
    private final Controller controller;
    public Connection(Socket conn, BlockingQueue<Request> requestQueue,Controller controller,String name) throws IOException {
        this.conn=conn;
        this.name=name;
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
                    System.out.println(m);
                    requestQueue.put(m);
                } catch (IOException | ClassNotFoundException e) {
                    if (e instanceof IOException){
                        System.out.println("Connection closed");
                    }else{
                        e.printStackTrace();
                    }
                    break;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            System.out.println("Connection closed");
        }finally {
            try {
                this.conn.close();
                controller.removeConnection(this.conn.hashCode(),this.name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
