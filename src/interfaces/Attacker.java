package interfaces;

import models.GameLogic.BattleGround;

public interface Attacker extends Effector<Destroyable> {
    int getDamage();
    void giveDamageTo(Destroyable destroyable, BattleGround battleGround);
}
