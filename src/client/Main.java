package client;

import client.frame.MyFrame;
import client.network.InputNetworkManager;
import client.network.OutputNetworkManager;
import message.*;

import java.io.*;
import java.net.ServerSocket;
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
        try(ServerSocket server = new ServerSocket(PORT)){
            System.out.println("Server started");
            Socket conn = server.accept();
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
        BlockingQueue<Boolean> responseQueue = new ArrayBlockingQueue<>(1);
        inputNetworkManager= new InputNetworkManager(pm, responseQueue);
        outputNetworkManager= new OutputNetworkManager(out, responseQueue);
        pm.setOutputNetworkManager(outputNetworkManager);
        //keep reading messages from the server
        while(true){
            try {
                Message message = (Message) in.readObject();
                if( message instanceof createObject){
                    inputNetworkManager.createObject(((createObject) message).getObject());
                } else if( message instanceof removeObject){
                    inputNetworkManager.removeObject(((removeObject) message).getId());
                } else if( message instanceof updateObject){
                    inputNetworkManager.updateObject(((updateObject) message).getObject());
                } else if (message instanceof selectObject){
                    inputNetworkManager.selectObject(((selectObject) message).getId());
                } else if(message instanceof unselectObject){
                    inputNetworkManager.unselectObject(((unselectObject) message).getId());
                } else if(message instanceof response){
                    inputNetworkManager.sendResponse(((response) message).getResult());
                }else{
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
