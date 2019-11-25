package server.startup;

import server.net.ClientHandler;

public class Startup {
    public static void main(String[] args){
        ClientHandler server = new ClientHandler(6789);
        server.run();
    }
}
