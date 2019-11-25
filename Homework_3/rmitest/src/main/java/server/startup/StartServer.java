package server.startup;

import server.controller.ServerController;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartServer {

    public static void main(String[] args) {
        try {
            new StartServer().startRegistry();
            Naming.rebind(ServerController.NAME_IN_REGISTRY, new ServerController());
            System.out.println("Server is running");
        } catch (RemoteException | MalformedURLException e){

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