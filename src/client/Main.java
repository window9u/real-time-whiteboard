package client;

import java.util.Scanner;

public class Main {

     public static void main(String[] args) {
         PaintingManager pm = new PaintingManager(null);
         new MyFrame(pm);
         Scanner scanner = new Scanner(System.in);
    }
}
