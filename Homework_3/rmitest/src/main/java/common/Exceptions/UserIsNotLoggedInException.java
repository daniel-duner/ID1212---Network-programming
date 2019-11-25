package common.Exceptions;

public class UserIsNotLoggedInException extends Exception{
    public UserIsNotLoggedInException(String reason){
        super(reason);
    }
}
