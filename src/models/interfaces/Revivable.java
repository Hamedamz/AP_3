package models.interfaces;

public interface Revivable extends Destroyable {
    void revive();
    int getReviveTime();
    void performLosses();
}
