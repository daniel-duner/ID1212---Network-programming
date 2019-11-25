package common;

import common.Exceptions.AccessDeniedException;
import common.Exceptions.UserIsNotLoggedInException;
import common.Exceptions.UserNameIsTakenException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;


public interface CallServer extends Remote {
    public static final String NAME_IN_REGISTRY = "serverController";

    boolean signup(Credentials cred) throws RemoteException, UserNameIsTakenException;

    boolean addFile(File file,Credentials cred) throws RemoteException, UserIsNotLoggedInException;

    File downloadFile(String fileName,Credentials cred) throws RemoteException, UserIsNotLoggedInException;

    Credentials login(Credentials cred,CallClient remoteNode) throws  RemoteException;

    LinkedList<File> getFiles(Credentials cred) throws UserIsNotLoggedInException, RemoteException;

    void logout(Credentials cred) throws RemoteException;

    boolean deleteFile(String fileName, Credentials cred) throws AccessDeniedException, RemoteException;
}
