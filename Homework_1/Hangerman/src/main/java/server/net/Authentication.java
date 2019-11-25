package server.net;

import server.model.GameServer;
import server.util.HeaderHandler;
import server.util.JWT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Authentication implements Runnable {
    private Socket connection;
    private HeaderHandler headerHandler;
    private PrintWriter writer;
    private BufferedReader reader;
    private String user;
    private String password;
    private JWT jwt;

    public Authentication(Socket connection,String user, String password){
        this.connection = connection;
        this.headerHandler = new HeaderHandler();
        this.user = user;
        this.password = password;
        this.jwt = new JWT();
        try{
            this.writer = new PrintWriter(this.connection.getOutputStream(), true);
            this.reader = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        authenticator();
    }

    private void authenticator(){
        while (true) {
            try{
            String authData = reader.readLine();
            while(!this.headerHandler.checkSize(authData, false)){
                authData = authData+reader.readLine();
            }
            //System.out.println("authdata: "+authData);
            String[] extractedData = authData.split("\\|");
            if(extractedData[1].equals(this.user) && extractedData[2].equals(this.password)){
                String token = this.jwt.generateToken(this.user);
                this.writer.println(this.headerHandler.addHeader("true",token));
                Thread newThread = new Thread(new GameServer(this.connection, token));
                newThread.start();
                return;
            }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
