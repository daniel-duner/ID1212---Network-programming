package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Client {
    // Client connection
    private InetSocketAddress hostAddress;
    private SocketChannel channel;

    // I/O
    private BufferedReader reader;

    public Client(){
    }

    public void start(){
        try{
            reader = new BufferedReader(new InputStreamReader(System.in));
            menu();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void connect(){
        hostAddress = new InetSocketAddress("localhost", 1337);
        try{
        channel = SocketChannel.open(hostAddress);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void disconnect(){
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String userRead(){
        try {
            String input = reader.readLine().toLowerCase();
            if(input != null){
                return input;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void menu(){
        while(true){
            print("Write [S] to start");
            String input = userRead();
            if(input != null){
                if(input.equals("s")){
                    connect();
                    if(!play()){
                        return;
                    }
                } else {
                    print("Wrong command, try again!");
                }
            }
        }
    }

    private boolean play(){
        new Thread(new Presenter(this.channel)).start();
        while(true){
            String input = userRead();
            if(input != null){
                if(input.equals("/quit")){
                    disconnect();
                    return false;
                }
                new ClientCommunicator(this.channel,input).start();
            }
        }
    }

    private void print(String msg){System.out.println(msg);}
}
