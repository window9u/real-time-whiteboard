package client;
import java.io.*;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        int PORT = 9999;
        while(true){
            String name= getName();
            if(name==null || name.equals("exit") ){
                break;
            }
            getConnection(PORT, name);
        }
    }
    private static String getName() {
        System.out.println("Enter your Name: ");
        System.out.println("Enter 'exit' to exit");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static void getConnection(int PORT, String name){
        try {
            Socket conn = new Socket("127.0.0.1", PORT);
            System.out.println("Server started");
            new Client(conn, name);
        } catch (IOException e) {
            System.out.println("Server not started");
            e.printStackTrace();
        }

    }
}
