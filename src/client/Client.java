package client;

import client.component.Painting;
import client.frame.MyFrame;
import client.network.InputNetworkManager;
import client.network.OutputNetworkManager;
import type.response.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Client {
    InputNetworkManager inputNetworkManager;
    OutputNetworkManager outputNetworkManager;
    ObjectInputStream in;
    ObjectOutputStream out;
    PaintingManager pm;
    Socket conn;
    public Client(Socket conn) throws IOException {
        this.conn = conn;
        initManager();
        runClient();
    }
    private void initManager() throws IOException {
        in = new ObjectInputStream(conn.getInputStream());
        out = new ObjectOutputStream(conn.getOutputStream());
        this.pm = new PaintingManager();
        new MyFrame(pm);
        BlockingQueue<Painting> createResponse = new ArrayBlockingQueue<>(1);
        BlockingQueue<Boolean> removeResponse = new ArrayBlockingQueue<>(1);
        BlockingQueue<Boolean> selectResponse = new ArrayBlockingQueue<>(1);
        BlockingQueue<Boolean> unselectResponse = new ArrayBlockingQueue<>(1);
        inputNetworkManager = new InputNetworkManager(pm, createResponse, removeResponse, selectResponse, unselectResponse);
        outputNetworkManager = new OutputNetworkManager(out, createResponse, removeResponse, selectResponse, unselectResponse);
        pm.setOutputNetworkManager(outputNetworkManager);
    }
    private void runClient(){
        while (true) {
            System.out.println("get response");
            try {
                Response response = (Response) in.readObject();
                if (response instanceof create) {
                    inputNetworkManager.create(((create) response));
                } else if (response instanceof remove) {
                    inputNetworkManager.remove((remove) response);
                } else if (response instanceof update) {
                    inputNetworkManager.update((update) response);
                } else if (response instanceof select) {
                    inputNetworkManager.select((select) response);
                } else if (response instanceof unselect) {
                    inputNetworkManager.unselect((unselect) response);
                } else if (response instanceof init) {
                    outputNetworkManager.init(((init) response).getCONNECTION_ID());
                    System.out.println("init connection" + ((init) response).getCONNECTION_ID());
                } else {
                    System.out.println("Unknown message type");
                    break;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
