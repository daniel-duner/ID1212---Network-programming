package client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ClientCommunicator extends Thread {
    private ByteBuffer message;
    private SocketChannel channel;
    private Selector selector;
    private SelectionKey key;

    public ClientCommunicator(SocketChannel channel, String message){
        this.channel = channel;
        this.message = messageConverter(message);
        setNoneBlocking();
        connect();
    }

    //Opens connection to channel
    private void connect(){
        try {
            selector = Selector.open();
            key = this.channel.register(this.selector,SelectionKey.OP_WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setNoneBlocking(){
        try {
            this.channel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Converts a string message to bytes and puts it in a ByteBuffer
    private ByteBuffer messageConverter(String message) {
        return ByteBuffer.wrap(message.getBytes());
    }

    //selects
    private void select(){
        try {
            selector.select();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(){
        try{
            channel.write(message);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void start(){
        while(true){
            select();
            if(key.isWritable()){
                sendMessage();
                break;
            }
        }
    }

}
