package client;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Message implements Runnable {
    private String message;
    private PrintWriter out;

    public Message(String message, PrintWriter out){
        this.message = message;
        this.out = out;
    }

    @Override
    public void run() {
        this.out.println(this.message);
    }
}
