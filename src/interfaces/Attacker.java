package interfaces;

import models.GameLogic.BattleGround;
import models.GameLogic.Exceptions.NoTargetFoundException;
import models.GameLogic.Map;

import java.util.ArrayList;

public interface Attacker extends Effector<Destroyable> {
    void giveDamageTo(Destroyable destroyable, BattleGround battleGround);
}
