package models.interfaces;

import models.GameLogic.BattleGround;
import models.GameLogic.Exceptions.NoTargetFoundException;

public interface Effector<T> {
    int getEffectRange();
    void findTarget(BattleGround battleGround) throws NoTargetFoundException;
    T getTarget();
    void removeTarget();
}
