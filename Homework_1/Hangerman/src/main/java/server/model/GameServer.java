package server.model;

import server.util.HeaderHandler;

import java.io.*;
import java.net.Socket;

public class GameServer implements Runnable {
    private Socket connection;
    private PrintWriter writer;
    private BufferedReader reader;
    private Game game;
    private boolean on;
    private HeaderHandler headerHandler;
    private String token;

    public GameServer(Socket connection, String token) {
        this.connection = connection;
        this.headerHandler = new HeaderHandler();
        this.on = true;
        this.token = token;
        setupIO();
    }

    private void setupIO(){
        try {
            this.writer = new PrintWriter(connection.getOutputStream(), true);
            this.reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            this.game = new Game();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        menuMode();
    }

    private void menuMode(){
        while(this.on){
            String input;
            output(true);
            try {
                input = reader.readLine();
                while(!this.headerHandler.checkSize(input, false)){
                    StringBuilder str = new StringBuilder(input+reader.readLine());
                    input = str.toString();
                }
                if (input.split("\\|")[1].toLowerCase().equals("s")){
                    this.game.setStandardSettings();
                    playMode();
                }
            } catch (IOException e) {
                return;
            }
        }
    }

    private void playMode() {
        int state = 0;
        while (state == 0) {
            output(false);
            String input;
            try {
               // Thread.sleep(10000);
                input = this.reader.readLine().toLowerCase();
                if (input != null) {
                    while(!this.headerHandler.checkSize(input,false)){
                        input = input+this.reader.readLine();
                    }
                    //System.out.println(!checkToken(input));
                    if(!checkToken(input)){
                        this.writer.println(this.headerHandler.addHeader("tokenFail", this.token));
                    } else{
                    this.game.guess(input);
                    if(this.game.getState().equals("lost")){
                        output(false);
                        this.game.setState("menu");
                        state = 1;
                    } else if(this.game.getState().equals("won")){
                        output(false);
                        this.game.setState("menu");
                        state = 2;
                    }
                    }
                } else {
                    this.on = false;
                    break;
                }
            } catch (Exception e) {
               return;
            }
        }
    }
    private boolean checkToken(String data){
        String incToken = data.split("\\|")[0].split(",")[1];
        if(incToken.equals(this.token)){
            return true;
        } else {
            return true;
        }
    }

    private void output(boolean start){
        if(!start){
            this.writer.println(this.headerHandler.addHeader(this.game.getData(), this.token));
        } else {
            this.writer.println(this.headerHandler.addHeader("menu", this.token));
        }
    }


}