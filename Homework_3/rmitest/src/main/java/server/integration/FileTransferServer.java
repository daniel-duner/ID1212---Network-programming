package server.integration;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class FileTransferServer implements Runnable {
    private int port;
    private final String FILE_DIRECTORY =  "C:\\Users\\danie\\Desktop\\ID1212\\Homework_3\\rmitest\\src\\main\\java\\server\\files\\";
    private Socket connection;
    private boolean upload;
    private String fileName;
    private LinkedList <Integer>transferPorts;

    public FileTransferServer(int port, boolean upload, String fileName, LinkedList<Integer> transferPorts){
        this.port = port;
        this.upload = upload;
        this.fileName = fileName;
        this.transferPorts = transferPorts;
    }

    @Override
    public void run(){
        try {
            ServerSocket listener = new ServerSocket(this.port);
            System.out.println("Waiting...");
            this.connection = listener.accept();
            if(upload){
                startFileUpload();
            } else {
                startFileDownload();
            }
            listener.close();
            transferPorts.push(port);
        } catch (Exception e) {
            transferPorts.push(port);
        }
    }

    private void startFileUpload(){
        try {
            InputStream input = this.connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_DIRECTORY+fileName));
            String line;
            while((line = reader.readLine()) != null){
                writer.write(line);
                writer.newLine();
                writer.flush();
            }
            writer.close();
            this.connection.close();
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startFileDownload(){
        try {
            OutputStream output = this.connection.getOutputStream();
            File file = new File(FILE_DIRECTORY+fileName);
            byte[] buffer = new byte[(int)file.length()];
            FileInputStream fileInput= new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInput);
            bufferedInputStream.read(buffer,0,buffer.length);
            output.write(buffer,0,buffer.length);
            output.flush();
            bufferedInputStream.close();
            output.close();
            this.connection.close();
            System.out.println("Done.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


