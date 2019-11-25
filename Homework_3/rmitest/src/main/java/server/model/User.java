package server.model;

import common.CallClient;
import common.Credentials;

public class User {
    private Credentials cred;
    private CallClient remoteNode;

    public User(Credentials cred, CallClient remoteNode){
        this.cred = cred;
        this.remoteNode = remoteNode;
    }

    public Credentials getCred() {
        return cred;
    }

    public CallClient getRemoteNode() {
        return remoteNode;
    }
}
