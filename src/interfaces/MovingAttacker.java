package interfaces;

import models.GameLogic.Entities.Entity;
import models.GameLogic.Map;
import models.GameLogic.Position;

public interface MovingAttacker extends Movable, Attacker {
    Position findAttackPosition(Entity target, Map map);

    // FIXME: 5/3/2018 it needs changes I think
}
