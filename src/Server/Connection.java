package Server;

import DataFormat.Request;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Connection implements Runnable {
    Socket socket;
    //this queue is only for write
    private final BlockingQueue<Request> requestQueue;
    private volatile boolean running=true;
    public Connection(Socket socket, BlockingQueue<Request> requestQueue){
        this.socket=socket;
        this.requestQueue=requestQueue;
    }
    @Override
    public void run() {

        try(ObjectInput objectInput= new ObjectInputStream(this.socket.getInputStream())){
            while(this.running){
                Request req=(Request) objectInput.readObject();
                this.requestQueue.put(req);
            }
        }catch (EOFException e) {
            System.out.println("Client has closed the connection.");
        }catch (IOException e){
            System.out.println("Received message: " + e.getMessage());
        }catch (ClassNotFoundException | InterruptedException e ){
            System.out.println("Class not found: " + e.getMessage());
        } finally {
            closeSocket();
        }

    }
    private void closeSocket(){
        try{
            if(this.socket!=null){
                socket.close();
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public void stopConnection(){
        this.running=false;
        closeSocket();
    }
}
