package common.Exceptions;

public class UserNameIsTakenException extends Exception{
    public UserNameIsTakenException(String reason){
        super(reason);
    }
}