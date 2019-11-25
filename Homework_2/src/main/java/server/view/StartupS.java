package server.view;

import server.net.Server;

public class StartupS {
    public static void main(String[] args){
        Server server = new Server();
        server.listen();
    }
}
