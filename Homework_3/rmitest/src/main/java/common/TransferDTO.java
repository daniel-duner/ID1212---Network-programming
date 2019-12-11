package common;

import java.io.Serializable;

public class TransferDTO implements Serializable {
    private int port;
    private String ipAddress;

    public TransferDTO(int port, String ipAddress){
        this.port = port;
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
