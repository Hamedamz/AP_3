package interfaces;

import models.GameLogic.BattleGround;

public interface Attacker extends Effector<Destroyable> {
    int getAttackSpeed();
    int getDamage();
    void giveDamageTo(Destroyable destroyable, BattleGround battleGround);
}
