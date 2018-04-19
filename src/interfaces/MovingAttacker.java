package interfaces;

import models.Entity;
import models.Map;
import models.Position;

public interface MovingAttacker extends Movable, Attacker {
    Position findAttackPosition(Entity target, Map map);
}
