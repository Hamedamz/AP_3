package models.GameLogic;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import models.SinglePlayer.SinglePlayerWorld;

import java.util.Timer;
import java.util.TimerTask;

public class GameEngine {
    public static final int DEFAULT_DURATION = 1000;

    private SinglePlayerWorld singlePlayerWorld;
    private VillageGameEngine villageGameEngine;
    private BattleGroundGameEngine battleGroundGameEngine;
    private boolean isAttacking = false;
    private ObjectProperty<Integer> duration = new SimpleObjectProperty<>(DEFAULT_DURATION);
    private Timer timer;
    private TimerTask updateTask;

    // isAttacking First Phase Only

    public GameEngine(SinglePlayerWorld singlePlayerWorld) {
        this.singlePlayerWorld = singlePlayerWorld;
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
                if (singlePlayerWorld.getBattleGround().isGameFinished()) {
                    isAttacking = false;
                }
            }
            villageGameEngine.update();
        }
    }

    public void loadNewVillage() {
        villageGameEngine.loadVillage(singlePlayerWorld.getMyVillage());
    }

    public void loadBattleGround() {
        battleGroundGameEngine.loadBattleGround(singlePlayerWorld.getBattleGround());
        isAttacking = true;
    }

    public void resetVillage(){
        villageGameEngine.reset();
    }

    public void changeDuration(Integer duration) {
        this.duration.setValue(duration);
    }
}
