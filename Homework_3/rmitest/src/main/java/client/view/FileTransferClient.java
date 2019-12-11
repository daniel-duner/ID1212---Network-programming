package client.view;

import common.TransferDTO;

import java.io.*;
import java.net.Socket;

public class FileTransferClient{
    private final String FILE_DIRECTORY =  "C:\\Users\\danie\\Desktop\\ID1212\\Homework_3\\rmitest\\src\\main\\java\\client\\download\\";
    private Socket connection;
    private BufferedReader in;
    private String url;
    private int port;
    private boolean upload;
    private String fileName;

    public FileTransferClient(TransferDTO transferDTO, String fileName, boolean upload) {
        this.url = transferDTO.getIpAddress();
        this.port = transferDTO.getPort();
        this.upload = upload;
        this.fileName = fileName;
    }

    public boolean run() {
        try {
            this.connection = new Socket(this.url, this.port);
            System.out.println("connected");
            if(upload){
                return startFileUpload();
            } else {
                return startFileDownload();
            }
        } catch (Exception e) {
            System.out.println("Server connection refused");
        }
        return false;
    }

    private boolean startFileDownload(){
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
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean startFileUpload(){
        try {
            OutputStream output = this.connection.getOutputStream();
            File file = new File(FILE_DIRECTORY+fileName);
            byte[] buffer = new byte[(int)file.length()];
            FileInputStream fileInput= new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInput);
            bufferedInputStream.read(buffer,0,buffer.length);
            output.write(buffer,0,buffer.length);
            output.flush();
            if (bufferedInputStream != null) bufferedInputStream.close();
            if (output != null) output.close();
            this.connection.close();
            System.out.println("Done.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
