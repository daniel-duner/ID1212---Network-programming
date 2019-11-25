package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallClient extends Remote {
    void contactClient(String msg) throws RemoteException;
}
