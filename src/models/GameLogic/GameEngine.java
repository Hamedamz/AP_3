package models.GameLogic;

public class GameEngine {
    private transient World world;
    private VillageGameEngine villageGameEngine;
    private BattleGroundGameEngine battleGroundGameEngine;
    private boolean isAttacking = false; //fixme put false in constructore
    // isAttacking First Phase Only

    public GameEngine(World world) {
        this.world = world;
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
        villageGameEngine = new VillageGameEngine(world.getMyVillage());
    }

    public void loadBattleGround(BattleGround battleGround) {
        battleGroundGameEngine = new BattleGroundGameEngine(battleGround);
        battleGroundGameEngine.loadBattleGround(world.getBattleGround());
    }
}
