package server.net;

import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler {
    int port;
    String user = "admin";
    String password = "123";

    public ClientHandler(int port) {
        this.port = port;
    }

    /**
     *
     * Waits for clients to connect and then sends the connection off to a new thread for authentication
     */
    public void run(){
        while(true) {
            try {
                ServerSocket listener = new ServerSocket(this.port);
                Socket connection = listener.accept();
                Thread t1 = new Thread(new Authentication(connection, this.user, this.password));
                t1.run();
            } catch (Exception e) {
            }
        }
    }
}
