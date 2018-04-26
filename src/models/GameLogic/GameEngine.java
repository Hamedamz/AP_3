package models.GameLogic;

public class GameEngine {
    private VillageGameEngine villageGameEngine;
    private BattleGroundGameEngine battleGroundGameEngine;
    private boolean isAttacking = false; //fixme put false in constructore
    // isAttacking First Phase Only

    public void update() {
        if(isAttacking) {
            battleGroundGameEngine.update();
            if(battleGroundGameEngine.isAttackFinished()) {
                isAttacking = false;
            }
        } else {
            villageGameEngine.update();
        }
    }
}
