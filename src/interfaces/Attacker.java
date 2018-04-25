package interfaces;

import models.GameLogic.BattleGround;

public interface Attacker {
    void giveDamageTo(Destroyable destroyable);
    Destroyable setTarget(BattleGround battleGround);
    Destroyable getTarget();
}
