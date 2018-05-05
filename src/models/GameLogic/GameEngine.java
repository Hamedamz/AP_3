package models.GameLogic;

public class GameEngine {
    private World world;
    private VillageGameEngine villageGameEngine;
    private BattleGroundGameEngine battleGroundGameEngine;
    private boolean isAttacking = false; //fixme put false in constructore
    // isAttacking First Phase Only

    public void update() {
        if(isAttacking) {
            battleGroundGameEngine.update();
            if(world.getBattleGround().isGameFinished()) {
                isAttacking = false;
            }
        } else {
            villageGameEngine.update();
        }
    }

    public void loadNewVillage() {
        villageGameEngine.loadVillage(world.getMyVillage());
    }

    public void loadBattleGround(BattleGround battleGround) {
        battleGroundGameEngine.loadBattleGround(world.getBattleGround());
    }
}
