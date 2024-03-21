package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9999); // 서버소켓 생성 9999번 포트
        // 쓰레드에 안전한 공유객체 생성 why? 웹소켓은 해당 클라이언트의 정보만 저장하지, 모든 웹소켓에 대한 정보는 없음
        List<PrintWriter> outList = Collections.synchronizedList(new ArrayList<>());

        //새로운 소켓이 들어올때마다 스레드를 생성하고 start()
        while(true){
            Socket socket = serverSocket.accept(); //사용자의 접속을 대기하고, 사용자가 접속시 socket 생성
            System.out.println("사용자접속: "+socket);
            ChatThread chatThread = new ChatThread(socket,outList);
            chatThread.start();
        }

    }
}

class ChatThread extends Thread {
    private final Socket socket;
    private final List<PrintWriter> outList;
    private PrintWriter out;
    private BufferedReader in;

    public ChatThread(Socket socket,List<PrintWriter> outList){
        this.socket = socket;
        this.outList = outList;

        //소켓으로부터 읽을수있는 객체와 쓸수있는 객체를 얻어서 저장 및 공유객체에 추가
        try{
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outList.add(out);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void run() {
        String line = null;
        try{
            while((line =in.readLine()) != null){
                //공유 객체에 번갈아가면서 작성
                for (PrintWriter pr : outList) {
                    pr.println(line);
                    pr.flush();
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally { // 접속이 끊어 질때
            try{
                //접속해제 메시지 여기서 보내면됨
                outList.remove(out);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            //공유 객체에 번갈아가면서 작성
            for (PrintWriter pr : outList) {
                pr.println("해당 클라이언트의 접속이 끊어졌습니다 " + pr);
                pr.flush();
            }
            try{
                socket.close();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }
}