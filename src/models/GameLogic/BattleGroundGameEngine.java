package models.GameLogic;

import interfaces.Attacker;
import interfaces.Destroyable;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.DefensiveBuilding;
import models.GameLogic.Entities.Troop.AttackerTroop;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.Exceptions.NoTargetFoundException;

import java.util.ArrayList;

public class BattleGroundGameEngine {
    private BattleGround battleGround;
    private boolean isGameFinished = false;

    public BattleGroundGameEngine(BattleGround battleGround) {
        this.battleGround = battleGround;
    }

    public void loadBattleGround(BattleGround battleGround) {
        this.battleGround = battleGround;
    }

    public void update() {
        battleGround.setNumberOfTroopsDeployed(new int[30][30]);
        isGameFinished = battleGround.isGameFinished();
    }

    public void updateTroopTarget() {
        ArrayList<Destroyable> listOfDefensiveUnits = new ArrayList<>(battleGround.getMap().getBuildings());
        for (Troop troop : battleGround.getTroops()) {
            if (troop instanceof AttackerTroop) {
                if (((AttackerTroop) troop).getTarget() == null || ((AttackerTroop) troop).getTarget().isDestroyed()) {
                    try {
                        ((AttackerTroop) troop).setTarget(listOfDefensiveUnits);
                    } catch (NoTargetFoundException e) {
                        isGameFinished = true;
                    }
                }
            }
        }
    }

    public void updateBuildingTarget() {
        ArrayList<Destroyable> destroyables = new ArrayList<>(battleGround.getTroops());
        for (Building building : battleGround.getMap().getBuildings()) {
            if (building instanceof DefensiveBuilding) {
                if (((Attacker) building).getTarget() == null || ((Attacker) building).getTarget().isDestroyed()) {
                    try {
                        ((Attacker) building).setTarget(destroyables);
                    } catch (NoTargetFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }



}
