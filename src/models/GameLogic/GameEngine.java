package models.GameLogic;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import models.World;

import java.util.Timer;
import java.util.TimerTask;

public class GameEngine {
    public static final int DEFAULT_DURATION = 1000;

    private World world;
    private VillageGameEngine villageGameEngine;
    private BattleGroundGameEngine battleGroundGameEngine;
    private boolean isAttacking = false;
    private ObjectProperty<Integer> duration = new SimpleObjectProperty<>(DEFAULT_DURATION);
    private Timer timer;
    private TimerTask updateTask;

    // isAttacking First Phase Only

    public GameEngine(World world) {
        this.world = world;
        villageGameEngine = new VillageGameEngine();
        battleGroundGameEngine = new BattleGroundGameEngine();
        setupTimerTask();
        duration.addListener((observable, oldValue, newValue) -> {
            setupTimerTask();
        });
    }

    private void setupTimerTask() {
        timer = new Timer();
        updateTask = new TimerTask() {
            @Override
            public void run() {
                update();
            }
        };
        timer.schedule(updateTask, 0, duration.get());
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

    /**
     * change duration between calling of update function
     * @param duration new duration
     */
    public void changeDuration(Integer duration) {
        this.duration.setValue(duration);
    }

    public Integer getDuration() {
        return this.duration.get();
    }
}
