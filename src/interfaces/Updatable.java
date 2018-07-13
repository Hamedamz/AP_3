package interfaces;

import models.GameLogic.BattleGround;

public interface Updatable {
    void update(BattleGround battleGround, int turnPerSecond, int turn);
}
