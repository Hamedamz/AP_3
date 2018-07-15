package models.GameLogic;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import models.World;
import models.setting.GameLogicConstants;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GameEngine {
    public static final int DEFAULT_DURATION = 40;

    private World world;
    private VillageGameEngine villageGameEngine;
    private BattleGroundGameEngine battleGroundGameEngine;
    private boolean isAttacking = false;
    private ObjectProperty<Integer> duration = new SimpleObjectProperty<>(DEFAULT_DURATION);
    private ScheduledThreadPoolExecutor sch;

    // isAttacking First Phase Only

    private int turn;

    public GameEngine(World world) {
        turn = 0;
        this.world = world;
        villageGameEngine = new VillageGameEngine();
        battleGroundGameEngine = new BattleGroundGameEngine();
        setupTimerTask();
        duration.addListener((observable, oldValue, newValue) -> {
            setupTimerTask();
        });
    }

    private void setupTimerTask() {
        if(sch != null) {
            sch.shutdown();
        }
        sch = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(5);
        sch.scheduleAtFixedRate(() -> update(), 0, duration.get(), TimeUnit.MILLISECONDS);
    }

    public void update() {
        new Thread(() -> {
            if(villageGameEngine.isRunning()) {
                if (isAttacking) {
                    battleGroundGameEngine.update();
                    if (world.getBattleGround().isGameFinished()) {
                        isAttacking = false;
                    }
                }
                if(turn % GameLogicConstants.DEFAULT_TURNS_PER_SEC == 0) {
                    villageGameEngine.update();
                }
            }
            turn++;
        }).start();
    }

    public void loadNewVillage() {
        turn = 0;
        villageGameEngine.loadVillage(world.getMyVillage());
    }

    public void loadBattleGround() {
        battleGroundGameEngine.loadBattleGround(world.getBattleGround());
        isAttacking = true;
    }

    public void resetVillage(){
        turn = 0;
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
