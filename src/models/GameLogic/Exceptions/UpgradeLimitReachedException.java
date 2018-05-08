package models.GameLogic.Exceptions;

public class UpgradeLimitReachedException extends Exception {
    public UpgradeLimitReachedException() {
        super("upgrade limit reached!");
    }
}
