package models.interfaces;

import models.GameLogic.BattleGround;
import models.GameLogic.Position;
import models.GameLogic.enums.MoveType;

import java.util.ArrayList;

public interface Movable extends Locatable, Effector<Destroyable> {
    void move();
    MoveType getTroopMoveType();
    void findPath(BattleGround battleGround);
    ArrayList<Position> getPath();
    int getSpeed();
}
