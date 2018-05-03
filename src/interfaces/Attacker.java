package interfaces;

import models.GameLogic.BattleGround;
import models.GameLogic.Map;

public interface Attacker {
    void giveDamageTo(Destroyable destroyable, Map map);
    Destroyable setTarget(BattleGround battleGround);
    Destroyable getTarget();
}
