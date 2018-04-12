package interfaces;

import models.BattleGround;
import models.Entity;

import java.util.ArrayList;

public interface FixedAttacker {
    void giveAttackTo(Destroyable destroyable);
    Destroyable setTarget(BattleGround battleGround);
    Destroyable getTarget();
}
