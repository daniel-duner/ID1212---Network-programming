package server.controller;

import common.*;
import common.Exceptions.AccessDeniedException;
import common.Exceptions.UserIsNotLoggedInException;
import common.Exceptions.UserNameIsTakenException;
import server.integration.DBH;
import server.integration.FileTransferServer;

import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServerController extends UnicastRemoteObject implements CallServer {
    public static final String NAME_IN_REGISTRY = "serverController";
    DBH db = new DBH();
    HashMap<String,CallClient> users = new HashMap();
    LinkedList<Integer> transferPorts= new LinkedList<>();

    public ServerController() throws RemoteException {
        transferPorts.push(12345);
        transferPorts.push(12346);
        transferPorts.push(12347);
    }


    @Override
    public boolean signup(Credentials cred) throws RemoteException, UserNameIsTakenException {
        return db.signUp(cred);
    }

    @Override
    public TransferDTO addFile(File file, Credentials cred) throws RemoteException, UserIsNotLoggedInException {
        if(checkCredentials(cred)){
            if(db.addFile(file)){
                Integer port;
                while((port = transferPorts.peekFirst()) == null){}
                new Thread(new FileTransferServer(port, true, file.getName(),transferPorts)).start();
                return new TransferDTO(port, "localhost");
            }
        } else {
            throw new UserIsNotLoggedInException("user is not logged in");
        }
        return null;
    }

    @Override
    public TransferDTO downloadFile(String fileName, Credentials cred) throws RemoteException, UserIsNotLoggedInException, NoSuchElementException {
        if(checkCredentials(cred)){
            File download = db.downloadFile(fileName);
            Integer port;
            while((port = transferPorts.peekFirst()) == null){}
            new Thread(new FileTransferServer(port,false,fileName, transferPorts)).start();
            if(users.containsKey(download.getOwner()) && cred.getUserName().equals(download.getOwner())){
                users.get(download.getOwner()).contactClient("Your file: "+download.getName()+" has been downloaded by: "+cred.getUserName());
            }
            return new TransferDTO(port, "localhost");
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
