package server.model;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Word implements Runnable{
    private String path = System.getProperty("user.dir")+"/src/main/resources/wordlist.txt";
    private File wordList;
    private String word;
    public HiddenWord hiddenWord;
    private SelectionKey key;
    private Selector selector;

    public Word(SelectionKey key, Selector selector){
        this.key = key;
        this.selector = selector;
    }

    public void run(){
       generateWordNio();
    }

    private void generateWordNio(){
        try{
            FileInputStream fis = new FileInputStream(path);
            FileChannel channel = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1);
            String word = "";
            int number = (int) (Math.random() * 51527);;
            for(int i = 0; i < number; i++) {
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
                        buffer.clear();
                        break;
                    }
                    str.append(converted);
                    buffer.clear();
                }
            }
            this.word = word;
            //System.out.println("the "+number+":d word is: "+word);
            this.hiddenWord =  new HiddenWord(this);
            key.interestOps(SelectionKey.OP_WRITE);
            selector.wakeup();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void loadFile(){
        try{
            //System.out.println(this.+this.fileName);
            this.wordList = new File(this.path);
        } catch (Exception e){
            e.printStackTrace();
        }
        try{
            generateWord();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void generateWord() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(this.wordList));
        String word = "";
        int number = (int) (Math.random() * 51529);
        for (int i = 0; i < number; i++) {
            try {
                word = reader.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.word = word;
    }

    public int getWordLength() {
        return this.word.length();
    }

    public ArrayList<Integer> charAt(char character) {
        ArrayList<Integer> indexOfChar = new ArrayList();
        for(int i = 0; i < this.word.length(); i++) {
            if( this.word.charAt(i) == character) {
                indexOfChar.add(i);
            }
        }
        return indexOfChar;
    }

    @Override
    public String toString() {
        return this.word;
    }
}

