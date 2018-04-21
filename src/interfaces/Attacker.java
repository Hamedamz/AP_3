package interfaces;

import models.BattleGround;
import models.Entity;

import java.util.ArrayList;

public interface Attacker {
    void giveDamageTo(Destroyable destroyable);
    Destroyable setTarget(BattleGround battleGround);
    Destroyable getTarget();
}
