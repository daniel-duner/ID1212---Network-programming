package server.model;

import java.util.ArrayList;

public class Game {
    private Word word;
    private HiddenWord hiddenWord;
    private int attempts;
    private int score;
    private String state;

    public Game(){
        this.score = 0;
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
                +this.hiddenWord.getHiddenWord()+"|"
                +this.attempts+"|"
                +this.score+"|"
                +this.hiddenWord.getGuessedChars()
                ).toString();
    }

    public void guess(String guess) {
        if(guess.length() == 1) {
            char character =  guess.charAt(0);
            if(!this.hiddenWord.checkIfGuessed(character)) {
                this.hiddenWord.addGuessedChar(character);
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
            this.hiddenWord.setWord(this.word.toString());
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
            this.hiddenWord.addChar(guess, index);
            if(this.getHiddenWord().equals(this.word.toString())){
                this.state = "won";
                this.score++;
            }
        }
    }

     public void setStandardSettings(){
        this.state = "play";
        this.word = new Word();
        this.hiddenWord =  new HiddenWord(this.word);
        this.attempts = 7;
    }

    public String getHiddenWord() {
        return this.hiddenWord.toString();
    }

}
