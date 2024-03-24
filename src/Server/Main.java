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
        List<socketWriter> outList = Collections.synchronizedList(new ArrayList<>());
         BlockingQueue<Request> requestQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);
        Controller controller = new Controller(outList, requestQueue);
        new Thread(controller).start();
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("Server started");
            while(true){
                try {
                    Socket connection = serverSocket.accept();
                    System.out.println("사용자접속: "+connection);
                    ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
                    controller.initConnection(out);
                    new Thread(new Connection(connection, requestQueue)).start();
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