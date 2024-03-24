package client;
import java.io.*;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        int PORT = 9999;
        try (Socket conn = new Socket("127.0.0.1", PORT)) {
            System.out.println("Server started");
            ObjectInputStream in = new ObjectInputStream(conn.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(conn.getOutputStream());
            new Client(in,out);
        } catch (IOException e) {
            System.out.println("Server not started");
            e.printStackTrace();
        }
    }
}
