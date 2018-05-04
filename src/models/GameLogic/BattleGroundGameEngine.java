package models.GameLogic;

import interfaces.Attacker;
import interfaces.Revivable;
import models.GameLogic.Entities.Troop.AttackerTroop;
import models.GameLogic.Entities.Troop.Troop;

public class BattleGroundGameEngine {
    private BattleGround battleGround;

    public BattleGroundGameEngine(BattleGround battleGround) {
        this.battleGround = battleGround;
    }

    public void loadBattleGround(BattleGround battleGround) {
        this.battleGround = battleGround;
    }

    public void update() {

    }

    public void updateTroopTarget() {
        for (Troop troop : battleGround.getTroops()) {
            if(troop instanceof AttackerTroop) {
                if(((AttackerTroop) troop).getTarget() == null || ((AttackerTroop) troop).getTarget().isDestroyed()) {
                    ((AttackerTroop) troop).setTarget(battleGround);
                }
            }
        }
    }


}
