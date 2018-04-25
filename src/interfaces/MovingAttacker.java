package interfaces;

import models.GameLogic.Entity;
import models.GameLogic.Map;
import models.GameLogic.Position;

public interface MovingAttacker extends Movable, Attacker {
    Position findAttackPosition(Entity target, Map map);
}
