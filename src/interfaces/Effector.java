package interfaces;

import models.GameLogic.BattleGround;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Exceptions.NoTargetFoundException;

import java.util.ArrayList;

public interface Effector<T> {
    int getEffectRange();
    void findTarget(BattleGround battleGround) throws NoTargetFoundException;
    T getTarget();
    void removeTarget();
}
