package client;

import client.frame.MyFrame;
import client.network.RequestManager;
import client.network.ResponseManager;
import type.response.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    RequestManager requestManager;
    ObjectInputStream in;
    ObjectOutputStream out;
    ResponseManager responseManager;
    DataManager dataManager;
    Socket conn;
    public Client(Socket conn) throws IOException {
        this.conn = conn;
        initManager();
        runClient();
    }
    private void initManager() throws IOException {
        in = new ObjectInputStream(conn.getInputStream());
        out = new ObjectOutputStream(conn.getOutputStream());
        this.dataManager = new DataManager();
        this.responseManager = new ResponseManager(dataManager);
        requestManager = new RequestManager(out);
        new MyFrame(dataManager,requestManager,responseManager);
    }
    private void runClient(){
        while (true) {
            try {
                Response response = (Response) in.readObject();
                System.out.println(response);
                if (response instanceof create) {
                    responseManager.create(((create) response).getObject(), response.isReply());
                } else if (response instanceof remove) {
                    responseManager.remove(((remove) response).getId());
                } else if (response instanceof update) {
                    responseManager.update(((update) response).getObject());
                } else if (response instanceof select) {
                    responseManager.select(((select) response).getId(), response.isReply());
                } else if (response instanceof unselect) {
                    responseManager.free(((unselect) response).getId(), response.isReply());
                } else if (response instanceof init) {
                    requestManager.init(((init) response).getCONNECTION_ID());
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
