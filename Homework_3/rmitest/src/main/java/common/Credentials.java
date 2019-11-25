package common;

import java.io.Serializable;

public class Credentials implements Serializable {
    private String userName;
    private String password;
    private boolean token = false;

    public Credentials(){

    }
    public Credentials(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(boolean token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public boolean getToken() {
        return token;
    }
}
