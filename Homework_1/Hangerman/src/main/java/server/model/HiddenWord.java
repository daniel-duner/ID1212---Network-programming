package server.model;

import java.util.ArrayList;

public class HiddenWord {
    private Word word;
    private char[] hiddenWord;
    private ArrayList<Character> guessedChars;

    public HiddenWord(Word word) {
        this.word = word;
        this.guessedChars = new ArrayList<Character>();
        generateHiddenWord();
    }

    private void generateHiddenWord() {
        int wordLength = this.word.getWordLength();
        this.hiddenWord = new char[wordLength];
        for (int i = 0; i < wordLength; i++) {
            this.hiddenWord[i] = '_';
        }
    }

    public void setWord(String word){
        for(int i = 0; i < this.word.getWordLength();i++){
            this.hiddenWord[i] = word.charAt(i);
        }
    }
    public void addGuessedChar(char character){
        this.guessedChars.add(character);
    }

    public void addChar(char character, ArrayList<Integer> index) {
        for(int i = 0; i < index.size();i++) {
            this.hiddenWord[index.get(i)] = character;
        }
    }

    public boolean checkIfGuessed(char character){
        return this.guessedChars.contains(character);
    }

    public String getGuessedChars(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < this.guessedChars.size(); i++) {
            if((i+1) != this.guessedChars.size()){
                str.append(guessedChars.get(i)+",");
            }else{
                str.append(guessedChars.get(i));
            }
        }
        return str.toString();
    }

    public String getHiddenWord() {
        return String.valueOf(this.hiddenWord);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        int wordLength = this.word.getWordLength();
        for(int i = 0; i < this.word.getWordLength();i++){
            if((i+1)!=wordLength){
                str.append(this.hiddenWord[i]);
            }
        }
        return str.toString();
    }
}
