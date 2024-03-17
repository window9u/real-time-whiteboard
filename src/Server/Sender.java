package Server;

import DataFormat.Response;

import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;

public class Sender implements Runnable{

    private final Vector<Socket> sockets;
    private final BlockingQueue<Response> responseQueue;
    public Sender(BlockingQueue<Response > res){
        this.sockets=new Vector<>();
        this.responseQueue=res;
    }
    public void addSocket(Socket newSocket){
        this.sockets.add(newSocket);
    }
    @Override
    public void run(){
        try{
            while(true){
                Response res=responseQueue.take();
                switch (res.type){
                    case UNICAST -> unicast();
                    case BROADCAST -> broadcast();
                }
            }
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
    }

    private void unicast(){

    }
    private void broadcast(){

    }


}
