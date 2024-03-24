package Server;

import type.request.Request;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args)  {
        int PORT = 9999;
        int QUEUE_SIZE = 100;
        //the list of output streams to send messages to all clients
        List<socketWriter> outList = Collections.synchronizedList(new ArrayList<>());
        //client's request to send messages to the centralized controller
         BlockingQueue<Request> requestQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);
        // the controller that process the request and send response to the clients
        Controller controller = new Controller(outList, requestQueue);
        //start the controller
        new Thread(controller).start();
        //start the server
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("Server started");
            while(true){
                //accept the connection from the client
                try(Socket connection = serverSocket.accept()) {
                    System.out.println("사용자접속: "+connection);
                    ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
                    controller.initConnection(out);
                    ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
                    new Thread(new Connection(in, requestQueue)).start();
                }catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}