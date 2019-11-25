package server.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class HeaderHandler {

    public String addHeader(String data){
        return getLength(data)+"|"+data;
    }

    public String addHeader(String data, String token){
        return getLength(data)+","+token+"|"+data;
    }

    private int getLength(String message){
        try{
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
            objOut.writeObject(message);
            objOut.flush();
            objOut.close();
            return byteOut.toByteArray().length;
        } catch (IOException e){
            e.printStackTrace();
        }
        return -1;
    }

    public boolean checkSize(String data, boolean auth ){
       // System.out.println(data);
        String[] extracted;
        if(auth){
            extracted = data.split("\\|",2);
            String head = extracted[0].split(",")[0];
            if(head.equals(String.valueOf(getLength(extracted[1])))){
                return true;
            }
        } else if(!auth){
            extracted = data.split("\\|",2);
            String head = extracted[0].split(",")[0];
            if(head.equals(String.valueOf(getLength(extracted[1])))){
                return true;
            }
        }
        return false;
    }
}
