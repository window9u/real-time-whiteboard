package Server;

import DataFormat.Request;
import DataFormat.Response;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class Main {
    public static void main(String[] args) {
        //requestQueue is for get request from client
        //it's managed by each connection
        //connections send request to Control thread.
        BlockingQueue<Request> requestQueue= new ArrayBlockingQueue<>(10);
        //responseQueue is for send response to client
        //it is managed by Sender Thread
        BlockingQueue<Response> responseQueue= new ArrayBlockingQueue<>(10);
        //make control thread
        Runnable control = new Control(requestQueue,responseQueue);
        Thread controlThread = new Thread(control);
        controlThread.start();
        //make sender thread
        Sender sender = new Sender(responseQueue);
        Thread senderThread = new Thread(sender);
        senderThread.start();
        //make listening socket
        try (ServerSocket serverSocket =new ServerSocket(12345)){
            System.out.println("server started listen of 12345");
            while (true) {
                //make connection to thread and continue listen
                Socket socket = serverSocket.accept();
                Runnable connection=new Connection(socket,requestQueue);
                Thread connectionThread=new Thread(connection);
                connectionThread.start();
                //register to Sender
                sender.addSocket(socket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

