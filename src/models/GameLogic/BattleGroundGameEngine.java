package models.GameLogic;

import interfaces.Revivable;

public class BattleGroundGameEngine {
    private BattleGround battleGround;

    public BattleGroundGameEngine(BattleGround battleGround) {
        this.battleGround = battleGround;
    }

    public void loadBattleGround(BattleGround battleGround) {
        this.battleGround = battleGround;
    }

    public void update() {
        // TODO: 4/26/2018 implement me
    }


}
