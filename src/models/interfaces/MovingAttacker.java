package models.interfaces;

public interface MovingAttacker extends Movable, Attacker {
    void setTarget(Destroyable destroyable);
}
