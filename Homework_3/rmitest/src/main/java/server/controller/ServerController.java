package server.controller;

import common.CallClient;
import common.CallServer;
import common.Credentials;
import common.Exceptions.AccessDeniedException;
import common.Exceptions.UserIsNotLoggedInException;
import common.Exceptions.UserNameIsTakenException;
import common.File;
import server.integration.DBH;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServerController extends UnicastRemoteObject implements CallServer {
    public static final String NAME_IN_REGISTRY = "serverController";
    DBH db = new DBH();
    HashMap<String,CallClient> users = new HashMap();

    public ServerController() throws RemoteException {
    }


    @Override
    public boolean signup(Credentials cred) throws RemoteException, UserNameIsTakenException {
        return db.signUp(cred);
    }

    @Override
    public boolean addFile(File file, Credentials cred) throws RemoteException, UserIsNotLoggedInException {
        if(checkCredentials(cred)){
            return db.addFile(file);
        } else {
            throw new UserIsNotLoggedInException("user is not logged in");
        }
    }

    @Override
    public File downloadFile(String fileName,Credentials cred) throws RemoteException, UserIsNotLoggedInException {
        if(checkCredentials(cred)){
            File download = db.downloadFile(fileName);
            if(users.containsKey(download.getOwner())){
                users.get(download.getOwner()).contactClient("Your file: "+download.getName()+" has been downloaded by: "+cred.getUserName());
            }
            return download;
        } else{
            throw new UserIsNotLoggedInException("user is not logged in");
        }
    }
    @Override
    public LinkedList<File>getFiles(Credentials cred) throws RemoteException, UserIsNotLoggedInException {
        if(checkCredentials(cred)){
            LinkedList<File> files = db.getFiles();
            ListIterator<File> it = files.listIterator(0);
            while(it.hasNext()){
                File file = it.next();
                if(users.containsKey(file.getOwner()) && !cred.getUserName().equals(file.getOwner())){
                    users.get(file.getOwner()).contactClient("Your file: "+file.getName()+" has been listed by: " + cred.getUserName());
                }
            }
            return db.getFiles();
        }else{
            throw new UserIsNotLoggedInException("user is not logged in");
        }
    }

    @Override
    public boolean deleteFile(String fileName, Credentials cred) throws AccessDeniedException,RemoteException {
        File removal = db.deleteFile(fileName, cred);
        if(removal != null){
            users.get(removal.getOwner()).contactClient("Your file: "+removal.getName()+" has been deleted by: " + cred.getUserName());
            return true;
        }
        return false;
    }


    @Override
    public Credentials login(Credentials cred,CallClient remoteNode) throws RemoteException {
        if(db.authorizeUser(cred)){
            users.put(cred.getUserName(), remoteNode);
            cred.setToken(true);
            return cred;
        } else {
            return cred;
        }
    }
    public void logout(Credentials cred) throws RemoteException{
        if(checkCredentials(cred)){
            users.remove(cred.getToken());
            cred.setToken(false);
        }
    }

    private boolean checkCredentials(Credentials cred){
        if(users.containsKey(cred.getUserName()) && cred.getToken()){
            return true;
        } else{
            return false;
        }
    }

}
