package Server;

import DataFormat.Request;
import DataFormat.Response;

import java.util.Vector;
import java.util.concurrent.BlockingQueue;

public class Control implements Runnable {

    private final Vector<String> ChatLog;
    private final BlockingQueue<Request> requestQueue;
    private final BlockingQueue<Response> responseQueue;
    public Control(BlockingQueue<Request> req,BlockingQueue<Response> res){
        this.ChatLog= new Vector<String>();
        this.requestQueue=req;
        this.responseQueue=res;
    }
    @Override
    public void run() {
        while(true){
            try {
                Request curChat=this.requestQueue.take();
                switch (curChat.type){
                    case MESSAGE -> {
                        this.ChatLog.add(curChat.content);
                        responseQueue.put(new Response(curChat.content,curChat.id,Response.TYPE.BROADCAST));
                    }case PREV ->
                        responseQueue.put(new Response(getPrev(),curChat.id,Response.TYPE.UNICAST));

                }
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
