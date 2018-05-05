package interfaces;

import models.GameLogic.Entities.Entity;
import models.GameLogic.Map;
import models.GameLogic.Position;
import models.GameLogic.enums.MoveType;

import java.util.ArrayList;

public interface Movable extends Effector<Destroyable> {
    void move(Position position, Map map);
    MoveType getTroopType();
    Position findActionPosition(Entity target, Map map);
    ArrayList<Position> getPath();
    void setPath(ArrayList<Position> path);
    int getSpeed();
}
