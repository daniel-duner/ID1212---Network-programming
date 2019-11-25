package server.view;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Test {
    private String file = System.getProperty("user.dir")+"/src/main/resources/wordlist.txt";


    private void loadFileNio(){
        try{
            FileInputStream fis = new FileInputStream(file);
            FileChannel channel = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1);
            String word = "";
            int limit = 51527;
            for(int i = 0; i < limit; i++) {
                int count = 0;
                StringBuilder str = new StringBuilder();
                while (true) {
                    count++;
                    channel.read(buffer);
                    String converted = new String(buffer.array(), StandardCharsets.US_ASCII);
                    if (converted.equals("\r")) {
                        buffer.clear();
                        channel.read(buffer);
                        word = str.toString();
                        print(word);
                        buffer.clear();
                        break;
                    }
                    str.append(converted);
                    buffer.clear();
                }
            }
            print("the "+limit+":d word is: "+word);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void print(String msg){
        System.out.println(msg);
    }

    public static void main(String[] args){
        Test test = new Test();
        test.loadFileNio();
    }
}
