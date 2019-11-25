package client;

import server.util.HeaderHandler;

import java.lang.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class Screen implements Runnable{
    private BufferedReader in;
    private Socket connection;
    private HeaderHandler headerHandler;

    public Screen(BufferedReader in,Socket connection, HeaderHandler headerHandler){
        this.headerHandler = headerHandler;
        this.in = in;
        this.connection = connection;
    }

    @Override
    public void run() {
        while(true){
            String serverMessage;
            try {
                serverMessage = this.in.readLine();
                if (serverMessage != null) {
                    while(!this.headerHandler.checkSize(serverMessage,false)){
                        serverMessage = serverMessage+this.in.readLine();
                    }
                    print(decode(serverMessage));
                } else {
                    break;
                }
            } catch (IOException e) {
                return;
            }
        }
    }

    private String[] decode(String codedMessage){
        return codedMessage.split("\\|");
    }


    private int checkMode(String[] decodedMessage){
        if(decodedMessage[1].equals("menu")){
            return 0;
        }else if(decodedMessage[1].equals("play")){
            return 1;
        }else if(decodedMessage[1].equals("won")){
            return 2;
        }else if(decodedMessage[1].equals("lost")){
            return 3;
        } else if(decodedMessage[1].equals("tokenFail"))
            return 4;
        return 0;
    }

    /**
     * PROTOCOL
     * 0. Header - string
     * 1. State - string
     * 2. Word - string
     * 3. Hidden word - string
     * 4. Attempts - int
     * 5. Score - int
     * 6. guessed characters
     **/
    private void print(String[] data){

            switch(checkMode(data)){
                /*Menu*/
                case 0:
                    System.out.println("\nWrite [S] to start\n");
                break;
                /*Play*/
                case 1:
                    System.out.print("\nWord: "+presentHiddenWord(data[3])
                        +"\nGuessed Characters: "+ guessedChars(data)
                        +"\nYour Score: "+ data[5] +"\nAttempts left: "+ data[4]
                        +"\nGuess on a word or a character\n\n"
                        );
                break;
                /*Won*/
                case 2:
                    System.out.print("\nThe Word: "+data[2]
                        +"\nYour Score is: "+ data[5]
                        +"\nCongratulations You guessed right!\n"
                        );
                break;
                /*Lost*/
                case 3:
                    System.out.print("\nYou are out of attempts, you have lost!"
                        +"\nThe Word was: "+data[2]
                        +"\nYour Score is: "+ data[5]+"\n"
                );
                break;
                case 4:
                    System.out.print("\nYou are not logged in\n");
                default: break;
            }
    }

    private String guessedChars (String[] data){
        if(data.length == 7){
            return presentHiddenWord(data[6]);
        }
        return "";
    }

    private String presentHiddenWord(String hiddenWord){
        StringBuilder str = new StringBuilder();
        int wordLength = hiddenWord.length();
        for(int i = 0; i < wordLength;i++){
            if((i+1) != wordLength){
                str.append(hiddenWord.charAt(i)+" ");
            } else {
                str.append(hiddenWord.charAt(i));
            }
        }
        return str.toString();
    }

}
