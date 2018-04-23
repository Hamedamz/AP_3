package interfaces;

import models.Entity;
import models.Map;
import models.Position;
import models.Troop;
import models.enums.MoveType;

public interface Movable {
    void move(Position position, Map map);
    MoveType getTroopType();
}
