package models.GameLogic.Exceptions;

public class NotAvailableAtThisLevelException extends Exception {
    public NotAvailableAtThisLevelException() {
        super("this troop is not available in this level!");
    }
}
