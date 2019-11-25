package client;
import server.util.HeaderHandler;

import java.lang.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Scanner inFromUser = new Scanner(System.in);
    private HeaderHandler headerHandler;
    private BufferedReader in;
    private PrintWriter out;
    private Socket connection;
    private String url;
    private int port;
    private String token;

    public Client(String url, int port) {
        this.headerHandler = new HeaderHandler();
        this.url = url;
        this.port = port;
    }

    private Socket connectToServer() {
        try {
            this.connection = new Socket(this.url, this.port);
            this.out = new PrintWriter(this.connection.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (Exception e) {
            System.out.println("Game server is down, please wait and try again");
            menu();
        }
        return null;
    }

    private void game() {
        Screen screen = new Screen(this.in, this.connection, this.headerHandler);
        Thread screenThread = new Thread(screen);
        screenThread.start();
        while (true) {
            String input = inFromUser.nextLine();
            if (!input.equals("")) {
                Thread messageThread = new Thread(new Message(headerHandler.addHeader(input,this.token), this.out));
                if (input.equals("/quit")) {
                    try {
                        this.connection.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                messageThread.start();
            }
        }

    }

    private void menu() {
        String input;
        while (true) {
            System.out.println("- - - - - H A N G M A N - - - - -");
            System.out.println("- - - - - C O N N E C T [C] - - - - -");
            System.out.println("- - - - - Q U I T [ Q ] - - - - -");
            input = this.inFromUser.nextLine().toLowerCase();
            if(input.equals("c")) {
                String identity = Authentication();
                this.connectToServer();
                Thread messageThread = new Thread(new Message(headerHandler.addHeader(identity), this.out));
                messageThread.run();
                try{
                    String response = this.in.readLine();
                    while(!this.headerHandler.checkSize(response, true)){
                        response = response+this.in.readLine();
                    }
                    String[] extractedData = response.split("\\|");
                    if(extractedData[1].equals("true")){
                        this.token = extractedData[0].split(",")[1];
                        game();
                        break;
                    } else {
                        System.out.println("Wrong username or password, try to connect again");
                    }
                } catch(IOException e){
                    e.printStackTrace();
                }
            }else if(input.equals("q")) {
                return;
            }else{
            System.out.println("Please choose a correct alternative");
            break;
            }
        }
    }

    private String Authentication(){
       String username;
       String password;
       System.out.println("Username:");
       username = this.inFromUser.nextLine();
       System.out.println("Password:");
       password = this.inFromUser.nextLine();
       return username+"|"+password;
    }


    public void start() {
        menu();
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 6789);
        client.start();
    }
}
