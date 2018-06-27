package models.GameLogic;

import java.util.Timer;
import java.util.TimerTask;

public class GameEngine {
    public static final int DEFAULT_DURATION = 100;

    private transient World world;
    private VillageGameEngine villageGameEngine;
    private BattleGroundGameEngine battleGroundGameEngine;
    private boolean isAttacking = false;
    private int duration = DEFAULT_DURATION;
    private Timer timer;
    private TimerTask updateTask;

    // isAttacking First Phase Only

    public GameEngine(World world) {
        this.world = world;
        villageGameEngine = new VillageGameEngine();
        battleGroundGameEngine = new BattleGroundGameEngine();
        timer = new Timer();
        updateTask = new TimerTask() {
            @Override
            public void run() {
                update();
            }
        };
        timer.schedule(updateTask, 0, duration);
    }

    public void update() {
        if(villageGameEngine.isRunning()) {
            if (isAttacking) {
                battleGroundGameEngine.update();
                if (world.getBattleGround().isGameFinished()) {
                    isAttacking = false;
                }
            }
            villageGameEngine.update();
        }
    }

    public void loadNewVillage() {
        villageGameEngine.loadVillage(world.getMyVillage());
    }

    public void loadBattleGround() {
        battleGroundGameEngine.loadBattleGround(world.getBattleGround());
        isAttacking = true;
    }

    public void resetVillage(){
        villageGameEngine.reset();
    }
}
