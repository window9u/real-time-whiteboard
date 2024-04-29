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
            getConnection(getHost(), PORT, name);
        }
    }
    private static String getHost(){
        System.out.println("Enter the Server's ip (in same sub network): ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
    private static void getConnection(String HOST,int PORT, String name){
        try {
            Socket conn = new Socket(HOST, PORT);
            System.out.println("Server started");
            new Client(conn, name).run();
        } catch (IOException e) {
            System.out.println("Server not started");
            e.printStackTrace();
        }

    }
}
