package client;

import client.component.Painting;
import client.frame.MyFrame;
import client.network.InputNetworkManager;
import client.network.OutputNetworkManager;
import type.response.*;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class Main {

    public static void main(String[] args) throws IOException {
        int PORT = 9999;
        ObjectInputStream in = null;
        ObjectOutputStream out=null;
        InputNetworkManager inputNetworkManager;
        OutputNetworkManager outputNetworkManager;
        try(Socket conn = new Socket("127.0.0.1",PORT)){
            System.out.println("Server started");
            in=new ObjectInputStream(conn.getInputStream());
            out=new ObjectOutputStream(conn.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
        PaintingManager pm = new PaintingManager();
        new MyFrame(pm);
        if(in==null || out==null){
            return;
        }
        BlockingQueue<Painting> createResponse = new ArrayBlockingQueue<>(1);
        BlockingQueue<Boolean> removeResponse = new ArrayBlockingQueue<>(1);
        BlockingQueue<Boolean> selectResponse = new ArrayBlockingQueue<>(1);
        BlockingQueue<Boolean> unselectResponse = new ArrayBlockingQueue<>(1);
        inputNetworkManager= new InputNetworkManager(pm, createResponse, removeResponse, selectResponse, unselectResponse);
        outputNetworkManager= new OutputNetworkManager(out, createResponse, removeResponse, selectResponse, unselectResponse);
        pm.setOutputNetworkManager(outputNetworkManager);
        //keep reading messages from the server
        while(true){
            try {
                Response response = (Response) in.readObject();
                if( response instanceof create){
                    inputNetworkManager.create(((create) response));
                } else if( response instanceof remove){
                    inputNetworkManager.remove((remove) response);
                } else if( response instanceof update){
                    inputNetworkManager.update((update) response);
                } else if (response instanceof select){
                    inputNetworkManager.select((select) response);
                } else if(response instanceof unselect){
                    inputNetworkManager.unselectObject((unselect) response);
                } else{
                    System.out.println("Unknown message type");
                    break;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
