package interfaces;

import models.Entity;
import models.Map;
import models.Position;

public interface MovingAttacker {
    Position findAttackPosition(Entity target, Map map);
}
