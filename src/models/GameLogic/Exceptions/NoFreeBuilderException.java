package models.GameLogic.Exceptions;

public class NoFreeBuilderException extends Exception {
    public NoFreeBuilderException() {
        super("You don’t have any worker to build this building");
    }
}
