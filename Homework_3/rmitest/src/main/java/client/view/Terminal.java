package client.view;

import common.*;
import common.Exceptions.UserIsNotLoggedInException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Terminal extends Thread {
    private Scanner userInput = new Scanner(System.in);
    private boolean on = true;
    private Communicator com;
    private boolean loggedOut = true;
    private Client me;
    private final String QUIT = "l";

    public void start(CallServer server) {
        while(true) {
            on = true;
            com = new Communicator(server, userInput);
            try {
                this.me = new Client();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Credentials cred = new Credentials();
            while (loggedOut) {
                while(true){
                    Print.print("\n[L]ogin");
                    Print.print("[N]ew account");
                    Print.print("[Q]uit\n");
                    String input = userInput.nextLine().toLowerCase();
                    if(input.equals("q")){
                        return;
                    } else if(input.equals("n")){
                        com.inputHandler("S");
                        break;
                    } else if(input.equals("l")){
                        break;
                    }
                }

                Print.print("write ur username: ");
                cred.setUserName(userInput.nextLine().toLowerCase());
                Print.print("write ur password");
                cred.setPassword(userInput.nextLine().toLowerCase());
                try {
                    boolean login = com.login(cred, me);
                    if (login) {
                        Print.print("Welcome! You are now logged in");
                        this.loggedOut = false;
                    } else {
                        Print.print("Login failed try again, wrong name or password");
                    }
                } catch (RemoteException e) {
                    Print.print("Login failed try again");
                }
            }
            while (on) {
                String input;

                Print.print("\n[A]dd a new file");
                Print.print("[D]ownload a file");
                Print.print("[R]emove a file");
                Print.print("[S]ee files");
                Print.print("[L]ogout\n");
                input = userInput.nextLine().toLowerCase();
                if (input.equals(QUIT)) {
                    com.inputHandler(input);
                    on = false;
                    loggedOut = true;
                    Print.print("You are now logged out");
                } else {
                    com.inputHandler(input);
                }
            }
        }
    }


    public class Client extends UnicastRemoteObject implements CallClient{

        public Client() throws RemoteException {
        }

        @Override
        public void contactClient(String msg) {
            Print.print(msg);
        }
    }

}
