package interfaces;

import models.GameLogic.Exceptions.NoTargetFoundException;

import java.util.ArrayList;

public interface MovingAttacker extends Movable, Attacker {
    void setTarget(Destroyable destroyable);
}
