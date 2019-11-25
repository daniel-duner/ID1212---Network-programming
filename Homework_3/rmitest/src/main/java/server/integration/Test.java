package server.integration;

import common.File;
import common.Print;

import java.sql.Connection;

public class Test {
    public static void main(String[] args){
        DBH db = new DBH();
        File file = new File("nyfil2","daniel", 25, false);
        if(db.addFile(file)){
            System.out.println("success");
        } else {
            System.out.println("failed");

        }
        File download = db.downloadFile("nyfil");
        Print.print(download.getName());
    }

}

