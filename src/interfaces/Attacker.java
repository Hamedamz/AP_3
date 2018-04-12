package interfaces;

import models.BattleGround;
import models.Entity;

import java.util.ArrayList;

public interface Attacker {
    void giveAttackTo(Destroyable destroyable);
    Destroyable setTarget(BattleGround battleGround);
    Destroyable getTarget();
}
