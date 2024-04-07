package Server;

import message.request.Request;
import message.request.init;

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
                    String name=getName(connection);
                    System.out.println("사용자접속: "+name);
                    ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
                    controller.initConnection(out, connection.hashCode());
                    new Thread(new Connection(connection, requestQueue,controller,name)).start();
                }catch (IOException e) {
                    e.printStackTrace();
                    break;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private String getName(Socket connection) throws IOException, ClassNotFoundException {
        ObjectInputStream in = (ObjectInputStream) connection.getInputStream();
        init initMessage =(init) in.readObject();
        return initMessage.getName();
    }
}
