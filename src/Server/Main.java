package Server;

import type.request.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args)  {
        //config setting
        int PORT = 9999;
        int QUEUE_SIZE = 100;
        List<socketWriter> outList = Collections.synchronizedList(new ArrayList<>());
        BlockingQueue<Request> requestQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);

        //start controller
        Controller controller = new Controller(outList, requestQueue);
        new Thread(controller).start();

        //start server
        Listener listener =new Listener(PORT, requestQueue);
        listener.startListen(controller);
    }
}