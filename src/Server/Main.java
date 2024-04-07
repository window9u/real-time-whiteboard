package Server;

import message.request.Request;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args)  {
        //config setting
        int PORT = 9999;
        int QUEUE_SIZE = 100;
        HashMap<Integer,socketWriter> outMap = new HashMap<>();
        BlockingQueue<Request> requestQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);

        //start controller
        Controller controller = new Controller(outMap, requestQueue);
        new Thread(controller).start();

        //start server
        Listener listener =new Listener(PORT, requestQueue);
        listener.startListen(controller);
    }
}