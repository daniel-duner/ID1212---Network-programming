package server.model;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Word {
    String fileName = "\\src\\resources\\wordlist.txt";
    String currentDirectory = System.getProperty("user.dir");
    private File wordList;
    private String word;

    public Word(){
       loadFile();
    }

    private void loadFile(){
        try{
            System.out.println(this.currentDirectory+this.fileName);
            this.wordList = new File(this.currentDirectory+this.fileName);
        } catch (Exception e){
            e.printStackTrace();
        }
        try{
            generateWord();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void generateWord() throws FileNotFoundException {
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

