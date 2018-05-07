package models.GameLogic.Exceptions;

public class InvalidPositionException extends Exception {
    public InvalidPositionException() {
        super("invalid position");
    }
}
