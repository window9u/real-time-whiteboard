package client;
import java.io.*;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        int PORT = 9999;
        try {
            Socket conn = new Socket("127.0.0.1", PORT);
            System.out.println("Server started");
            new Client(conn);
        } catch (IOException e) {
            System.out.println("Server not started");
            e.printStackTrace();
        }
    }
}
