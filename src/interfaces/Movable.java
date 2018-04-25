package interfaces;

import models.GameLogic.Map;
import models.GameLogic.Position;
import models.GameLogic.enums.MoveType;

public interface Movable {
    void move(Position position, Map map);
    MoveType getTroopType();
}
