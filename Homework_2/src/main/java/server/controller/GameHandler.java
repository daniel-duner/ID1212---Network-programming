package server.controller;

import com.sun.org.apache.bcel.internal.generic.Select;
import server.model.Game;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class GameHandler {
    Game game;

    public GameHandler(SelectionKey key, Selector selector){
        init(key,selector);
    }
    private void init(SelectionKey key, Selector selector){
        game = new Game(key,selector);
    }

    public String getStartState(){
        String state = game.getData();
        game.setState("play");
        return state;
    }

    public void messageHandler(String input){
        String state = game.getState();
        if(state.equals("won") || state.equals("lost") || state.equals("end")){
            if(input.equals("s")){
                game.setStandardSettings();
            } else {
                game.setState("end");
            }
        } else{
            guess(input);
        }
    }

    private void guess(String input){
        game.guess(input);
    }

    public String getState(){
        return game.getData();
    }



}
