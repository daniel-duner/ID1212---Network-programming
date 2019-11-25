package client.view;

import com.sun.org.apache.regexp.internal.RE;
import common.CallServer;
import common.Credentials;
import common.Exceptions.AccessDeniedException;
import common.Exceptions.UserIsNotLoggedInException;
import common.Exceptions.UserNameIsTakenException;
import common.File;
import common.Print;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Communicator {
    private CallServer server;
    private Scanner userInput;
    private Credentials cred;
    private final String ADD_NEW_FILE = "a";
    private final String DOWNLOAD_FILE = "d";
    private final String SEE_ALL_FILES = "s";
    private final String LOGOUT = "l";
    private final String REMOVE_FILE = "r";
    private final String SIGNUP = "S";



    public Communicator(CallServer server, Scanner userInput){
        this.server = server;
        this.userInput = userInput;
    }

    public void signup(){
        while(true){
            Credentials credentials = new Credentials();
            Print.print("Write a username:");
            credentials.setUserName(userInput.nextLine().toLowerCase());
            Print.print("Write a password:");
            credentials.setPassword(userInput.nextLine().toLowerCase());
            try {
                if (server.signup(credentials)) {
                    Print.print("Registration was successful, please login!");
                    break;
                }
            } catch (UserNameIsTakenException e){
                Print.print("Username was taken, choose another username");
            } catch (RemoteException e){
                e.printStackTrace();
            }
        }
    }

    public boolean login(Credentials cred,Terminal.Client me) throws RemoteException {
        this.cred = server.login(cred, me);
        return this.cred.getToken();
    }

    public void inputHandler(String input){
        switch (input){
            case SIGNUP:
                signup();
                break;
            case ADD_NEW_FILE:
                addFile();
                break;
            case DOWNLOAD_FILE:
                downloadFile();
                break;
            case SEE_ALL_FILES:
                getFiles();
                break;
            case REMOVE_FILE:
                deleteFile();
                break;
            case LOGOUT:
                logout();
                break;
            default:
        }
    }
    private void downloadFile(){
        String input;
        while(true){
            Print.print("type in the name of the file you want to download or /quit if u want to quit");
            input = userInput.nextLine().toLowerCase();
            if(input.equals("/quit")){
                break;
            } else {
                try{
                    File download = server.downloadFile(input, cred);
                    if(download != null){
                        Print.print("Your file was succesfully uploaded");
                        Print.print("File name: "+download.getName()+" Owner: "+download.getOwner()+" File size: "+download.getSize());
                        break;
                    } else{
                        Print.print("A file with that name does not exist, try again");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (UserIsNotLoggedInException e){
                    Print.print("You are not logged in ");
                }
            }


        }
    }
    private void deleteFile(){
        String input;
        while(true){
            Print.print("type in the name of the file you want to delete or /quit if u want to quit");
            input = userInput.nextLine().toLowerCase();
            if(input.equals("/quit")){
                break;
            } else {
                try{
                    if(server.deleteFile(input,cred)){
                        Print.print("The file was succesfully removed");
                        break;
                    } else{
                        Print.print("A file with that name does not exist, try again");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (AccessDeniedException e) {
                    Print.print("You do not have the permission to remove that file");
                } catch (NoSuchElementException e){
                    Print.print("A file with that name does not exist, try again");
                }
            }


        }
    }

    public void logout(){
        try{
            server.logout(cred);
        } catch (RemoteException e){
            e.printStackTrace();
        }
    }

    private void addFile(){
        String input;
        String input2;
        boolean readOnly = false;
        while(true){
            Print.print("type in the name of the file or /quit if u want to quit");
            input = userInput.nextLine().toLowerCase();
            if(input.equals("/quit")){
                break;
            } else {
                while (true) {
                    Print.print("Do you want the file to be read only? [T]rue ir [F]alse or write /quit if u want to quit");
                    input2 = userInput.nextLine().toLowerCase();
                    if(input2.equals("/quit")){
                        break;
                    } else if(input2.equals("t")){
                        readOnly = true;
                        break;
                    }else if( input2.equals("f")){
                        readOnly = false;
                        break;
                    }
                    Print.print("Invalid command");
                }
                if(input2.equals("/quit")){
                    break;
                }
                else {
                    File newFile = new File(input, cred.getUserName(), getFileSize(input), readOnly);
                    try {
                        if (server.addFile(newFile, cred)) {
                            Print.print("Your file was succesfully uploaded");
                            break;
                        } else {
                            Print.print("Your file failed to upload, a file of the same name might exist, try again");
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (UserIsNotLoggedInException e) {
                        Print.print("You are not logged in ");
                    }
                }
            }


        }
    }

    private void getFiles(){
        try {
            LinkedList<File> files = server.getFiles(cred);
            if(!files.isEmpty()) {
                Print.print("-------------------FILE CATALOG ---------------------");
                while (!files.isEmpty()) {
                    File f = files.pop();
                    Print.print("FileName: " + f.getName() + " Owner: " + f.getOwner() + " File size: " + f.getSize()+" Permission: "+f.getPermission());
                }
                Print.print("-------------------END OF CATALOG ---------------------");
                Print.print("");
            } else {
                Print.print("There are no files in the catalog");
            }
        }catch (UserIsNotLoggedInException | RemoteException e){
            Print.print("You are not logged in ");
        }
    }


    private int getFileSize(String input){
        return input.getBytes().length*2;

    }


}
