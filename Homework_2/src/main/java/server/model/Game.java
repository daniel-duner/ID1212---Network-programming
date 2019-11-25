package server.model;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayList;

public class Game {
    private Word word;
    private int attempts;
    private int score;
    private String state;
    private SelectionKey key;
    private Selector selector;

    public Game(SelectionKey key, Selector selector){
        this.score = 0;
        this.key = key;
        this.selector = selector;
        setStandardSettings();
    }
    public String getState(){
        return this.state;
    }

    public void setState(String state){
        this.state = state;
    }

    public String getData(){
        return new StringBuilder(
                this.state+"|"
                        +this.word.toString()+"|"
                        +this.word.hiddenWord.getHiddenWord()+"|"
                        +this.attempts+"|"
                        +this.score+"|"
                        +this.word.hiddenWord.getGuessedChars()
        ).toString();
    }

    public void guess(String guess) {
        if(guess.length() == 1) {
            char character =  guess.charAt(0);
            if(!this.word.hiddenWord.checkIfGuessed(character)) {
                this.word.hiddenWord.addGuessedChar(character);
                guessChar(guess.charAt(0));
            }
        } else {
            guessWord(guess);
        }
        if(attempts == 0){
            this.state = "lost";
            score--;
        }
    }

    private void guessWord(String guess){
        if(this.word.toString().equals(guess)){
            this.word.hiddenWord.setWord(this.word.toString());
            this.state = "won";
            this.score++;
        } else {
            this.attempts--;
        }
    }

    private void guessChar(char guess) {
        ArrayList<Integer> index = this.word.charAt(guess);
        if(index.size() < 1) {
            this.attempts--;
        } else {
            this.word.hiddenWord.addChar(guess, index);
            if(this.getHiddenWord().equals(this.word.toString())){
                this.state = "won";
                this.score++;
            }
        }
    }

    public void setStandardSettings(){
        this.state = "start";
        this.word = new Word(key, selector);
        key.interestOps(SelectionKey.OP_CONNECT);
        new Thread(this.word).start();
        System.out.println("I am alive");
        this.attempts = 7;
    }

    public String getHiddenWord() {
        return this.word.hiddenWord.toString();
    }

}
