package client;

import client.painting.PaintingManager;

import javax.swing.*;

public class Main {

     public static void main(String[] args) {
         PaintingManager pm = new PaintingManager();
         new MyFrame(pm);

    }
}
