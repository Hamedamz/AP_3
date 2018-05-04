package interfaces;

import models.GameLogic.BattleGround;
import models.GameLogic.Map;

import java.util.ArrayList;

public interface Attacker {
    void giveDamageTo(Destroyable destroyable, Map map);
    Destroyable setTarget(ArrayList<Destroyable> destroyables);
    Destroyable getTarget();
}
