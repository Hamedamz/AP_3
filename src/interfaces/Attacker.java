package interfaces;

import models.GameLogic.BattleGround;
import models.GameLogic.Exceptions.NoTargetFoundException;
import models.GameLogic.Map;

import java.util.ArrayList;

public interface Attacker {
    void giveDamageTo(Destroyable destroyable, Map map);
    void setTarget(ArrayList<Destroyable> destroyables) throws NoTargetFoundException;
    Destroyable getTarget();
}
