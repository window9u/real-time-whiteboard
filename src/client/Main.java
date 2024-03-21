package client;

import client.frame.MyFrame;


public class Main {

     public static void main(String[] args) {
         PaintingManager pm = new PaintingManager(null);
         new MyFrame(pm);
    }
}
