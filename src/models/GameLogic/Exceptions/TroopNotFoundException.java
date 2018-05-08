package models.GameLogic.Exceptions;

public class TroopNotFoundException extends Exception {
    public TroopNotFoundException() {
        super("troop not found!");
    }
}
