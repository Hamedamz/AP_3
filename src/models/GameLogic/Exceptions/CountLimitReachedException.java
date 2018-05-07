package models.GameLogic.Exceptions;

public class CountLimitReachedException extends Exception {
    public CountLimitReachedException() {
        super("count limit reached for this item!");
    }
}
