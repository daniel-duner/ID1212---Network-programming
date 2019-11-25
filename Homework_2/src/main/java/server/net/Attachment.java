package server.net;

import server.controller.GameHandler;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class Attachment {
    public String state;
    public GameHandler gameHandler;

    public Attachment(String state){
        this.state = state;
    }

    public void start(SelectionKey key, Selector selector){
        gameHandler = new GameHandler(key, selector);
        setState("play");
    }

    public void setState(String state){
        this.state = state;
    }
}
