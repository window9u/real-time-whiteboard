package Server;

import type.request.Request;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Listener {
    private final BlockingQueue<Request> requestQueue;
    private final int PORT_NUM;
    public Listener(int port, BlockingQueue<Request> requestQueue){
        PORT_NUM = port;
        this.requestQueue = requestQueue;
    }
    public void startListen(Controller controller){
        try(ServerSocket serverSocket = new ServerSocket(PORT_NUM)){
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
