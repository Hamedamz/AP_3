package models.GameLogic.Exceptions;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
        super("Wrong Password!");
    }
}
