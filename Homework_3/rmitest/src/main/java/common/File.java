package common;

import java.io.Serializable;

public class File implements Serializable {
    private String name;
    private String owner;
    private String permission;
    private int size;

    public File(String name, String owner, int size,boolean readOnly){
        this.name = name;
        this.owner = owner;
        this.size = size;
        if(readOnly){
            this.permission = "r";
        } else {
            this.permission = "rw";
        }
    }

    public void setPermission(boolean readOnly) {
        if(readOnly){
            this.permission = "r";
        }  else {
            this.permission ="rw";
        }
    }

    public String getPermission() {
        return permission;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }
}
