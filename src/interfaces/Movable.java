package interfaces;

import models.GameLogic.BattleGround;
import models.GameLogic.Position;
import models.GameLogic.enums.MoveType;

import javax.xml.stream.Location;
import java.util.ArrayList;

public interface Movable extends Locatable, Effector<Destroyable> {
    void move();
    MoveType getTroopMoveType();
    void findPath(BattleGround battleGround);
    ArrayList<Position> getPath();
    int getSpeed();
}
