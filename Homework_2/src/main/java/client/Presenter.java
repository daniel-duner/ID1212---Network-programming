package client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Presenter implements Runnable {
    private SocketChannel channel;
    private Selector selector;
    private SelectionKey key;

    public Presenter(SocketChannel channel) {
        this.channel = channel;
        setNoneBlocking();
        connect();
    }

    //Opens connection to channel
    private void connect() {
        try {
            selector = Selector.open();
            key = this.channel.register(this.selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setNoneBlocking(){
        try {
            this.channel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void presentRead() {

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        try {
            channel.read(buffer);
            buffer.flip();
            byte[] arg = new byte[buffer.remaining()];
            buffer.get(arg);
            //print(new String(arg));
            printState((new String(arg)).split("\\|"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //selects
    private void select(){
        try {
            selector.select();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void print(String msg){System.out.println(msg);}

    public void run(){
        while(true){
            select();
            if(key.isReadable()){
                presentRead();
            }
        }
    }

    private int checkMode(String[] decodedMessage) {
        //print(decodedMessage[0]+" "+decodedMessage[1]);
        if (decodedMessage[0].equals("start")) {
            return 0;
        } else if (decodedMessage[0].equals("play")) {
            return 1;
        } else if (decodedMessage[0].equals("won")) {
            return 2;
        } else if (decodedMessage[0].equals("lost")) {
            return 3;
        } else if(decodedMessage[0].equals("end")){
            return 4;
        }
            return 0;
    }

    /**
     * PROTOCOL
     * 0. State - string
     * 1. Word - string
     * 2. Hidden word - string
     * 3. Attempts - int
     * 4. Score - int
     * 5. guessed characters
     **/
    private void printState(String[] data) {

        switch (checkMode(data)) {
            /*start*/
            case 0:
                print("\nWord: " + presentHiddenWord(data[2])
                        + "\nGuessed Characters: " + guessedChars(data)
                        + "\nYour Score: " + data[4] + "\nAttempts left: " + data[3]
                        + "\n--------------------------------"
                        + "\n Guess on a word or a character"
                        + "\n--------------------------------\n\n"
                );
                break;
            /*Play*/
            case 1:
                System.out.print("\nWord: " + presentHiddenWord(data[2])
                        + "\nGuessed Characters: " + guessedChars(data)
                        + "\nYour Score: " + data[4] + "\nAttempts left: " + data[3]
                        + "\n--------------------------------"
                        + "\n Guess on a word or a character"
                        + "\n--------------------------------\n\n"
                );
                break;
            /*Won*/
            case 2:
                System.out.print("\nThe Word: " + data[1]
                        + "\nYour Score is: " + data[4]
                        + "\nCongratulations You guessed right!"
                        +"\n\n Write [s] to start a new game!\n"
                );
                break;
            /*Lost*/
            case 3:
                System.out.print("\nYou are out of attempts, you have lost!"
                        + "\nThe Word was: " + data[1]
                        + "\nYour Score is: " + data[4]
                        +"\n\n Write [s] to start a new game!\n"

                );
                break;
            case 4:
                System.out.print("\n Write [s] to start a new game!"
                        + "\nYour Score is: " + data[4] + "\n"
                );
                break;
            default:
                break;
        }
    }

    private String guessedChars(String[] data) {
        if (data.length == 6) {
            return data[5];
        }
        return "";
    }

    private String presentHiddenWord(String hiddenWord) {
        StringBuilder str = new StringBuilder();
        int wordLength = hiddenWord.length();
        for (int i = 0; i < wordLength; i++) {
            if ((i + 1) != wordLength) {
                str.append(hiddenWord.charAt(i) + " ");
            } else {
                str.append(hiddenWord.charAt(i));
            }
        }
        return str.toString();
    }

}
