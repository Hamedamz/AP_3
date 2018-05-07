package models.GameLogic.Exceptions;

public class NotEnoughResourcesException extends Exception{
    public NotEnoughResourcesException() {
        super("not enough resources");
    }
}
