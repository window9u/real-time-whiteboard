package client;

import client.frame.MyFrame;
import message.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {

    public static void main(String[] args) throws IOException {
        int PORT = 9999;
        ObjectInputStream in = null;
        ObjectOutputStream out=null;
        NetworkManager nm;
        try(ServerSocket server = new ServerSocket(PORT)){
            System.out.println("Server started");
            Socket conn = server.accept();
            in=new ObjectInputStream(conn.getInputStream());
            out=new ObjectOutputStream(conn.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
        PaintingManager pm = new PaintingManager(null);
        new MyFrame(pm);
        if(in==null || out==null){
            return;
        }
        nm= new NetworkManager(out,pm);
        //keep reading messages from the server
        while(true){
            try {
                Message message = (Message) in.readObject();
                if( message instanceof createObject){
                    nm.createObject(((createObject) message).getObject());
                } else if( message instanceof removeObject){
                    nm.removeObject(((removeObject) message).getId());
                } else if( message instanceof updateObject){
                    nm.updateObject(((updateObject) message).getObject());
                } else if (message instanceof selectObject){
                    nm.selectObject(((selectObject) message).getId());
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
