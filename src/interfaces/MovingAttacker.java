package interfaces;

import models.Entity;
import models.Map;
import models.Position;

public interface MovingAttacker extends Movable, FixedAttacker{
    Position findAttackPosition(Entity target, Map map);
}
