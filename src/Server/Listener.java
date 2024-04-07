package Server;

import message.request.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
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
                    ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
                    controller.initConnection(out,in, connection.hashCode());
                    new Thread(new Connection(connection,in, requestQueue,controller)).start();
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
