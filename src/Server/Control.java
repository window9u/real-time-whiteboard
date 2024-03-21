package Server;

import DataFormat.Request;
import DataFormat.Response;

import java.io.PrintWriter;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;

public class Control implements Runnable {

    private final Vector<String> ChatLog;
    private final BlockingQueue<Request> requestQueue;
    private final Vector<PrintWriter> connections;
    public Control(BlockingQueue<Request> req,Vector<PrintWriter> connections){
        this.ChatLog= new Vector<String>();
        this.connections=connections;
        this.requestQueue=req;
    }
    @Override
    public void run() {
        while(true){
            try {
                Request curChat=this.requestQueue.take();
            }catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
    }
    private String getPrev(){
        String str="";
        for (String cur: this.ChatLog) {
            str=str.concat(cur+"\n");
        }
        return str;
    }

}
