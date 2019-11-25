package client.startup;

import client.view.Terminal;
import common.CallServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartClient {
    public static void main(String[] args) {
        try {
            new StartClient().startRegistry();
            //Naming.rebind(ClientController.NAME_IN_REGISTRY, new ClientController());
            CallServer server = (CallServer) Naming.lookup(CallServer.NAME_IN_REGISTRY);
            System.out.println("Client is running");
            new Terminal().start(server);
        } catch (RemoteException | MalformedURLException | NotBoundException e){
            e.printStackTrace();
        }
    }

    private void startRegistry() throws RemoteException{
        try{
            LocateRegistry.getRegistry().list();
        }catch (RemoteException noReistry){
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        }
    }
}