package Server;

import message.Message;

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
        List<ObjectOutputStream> outList = Collections.synchronizedList(new ArrayList<>());
         BlockingQueue<Message> messageQueue = new ArrayBlockingQueue<>(100);
        Controller controller = new Controller(outList,messageQueue);
        new Thread(controller).start();
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("Server started");
            while(true){
                try(Socket connection = serverSocket.accept()) {
                    System.out.println("사용자접속: "+connection);
                    outList.add(new ObjectOutputStream(connection.getOutputStream()));
                    ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
                    new Thread(new Connection(in,messageQueue));
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