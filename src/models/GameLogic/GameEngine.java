package models.GameLogic;

import java.util.LinkedHashMap;

public class GameEngine {
    private transient World world;
    private VillageGameEngine villageGameEngine;
    private BattleGroundGameEngine battleGroundGameEngine;
    private boolean isAttacking = false; //fixme put false in constructore
    // isAttacking First Phase Only

    public GameEngine(World world) {
        this.world = world;
        villageGameEngine = new VillageGameEngine();
    }

    public void update() {
        if (isAttacking) {
            battleGroundGameEngine.update();
            if (world.getBattleGround().isGameFinished()) {
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
        battleGroundGameEngine = new BattleGroundGameEngine(battleGround);
        battleGroundGameEngine.loadBattleGround(world.getBattleGround());
    }
}
