package interfaces;

import models.GameLogic.BattleGround;

public interface Attacker extends Effector<Destroyable> {
    void giveDamageTo(Destroyable destroyable, BattleGround battleGround);
}
