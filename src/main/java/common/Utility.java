package main.java.common;

import java.util.Scanner;

public class Utility {

    private static Scanner sc = new Scanner(System.in);

    public static int readInt(String msg) {
        while(true) {
            try {
                System.out.print(msg);
                return sc.nextInt();
            } catch(Exception e) {
                System.out.println("❌ Enter valid number!");
                sc.nextLine();
            }
        }
    }


    public static String readString(String message) {
        System.out.print(message);
        return sc.next();
    }

    public static String readLine(String message) {
        System.out.print(message);
        sc.nextLine();
        return sc.nextLine();
    }
}
