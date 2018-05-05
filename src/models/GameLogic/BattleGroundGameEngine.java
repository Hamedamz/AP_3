package models.GameLogic;

import interfaces.Destroyable;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.DefensiveBuilding;
import models.GameLogic.Entities.Defender;
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
        ArrayList<Destroyable> listOfDefensiveUnits = new ArrayList<>(battleGround.getVillage().getMap().getBuildings());
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
        for (Building building : battleGround.getMap().getBuildings()) {
            if (building instanceof DefensiveBuilding) {
                if (((DefensiveBuilding) building).getTarget() == null || ((DefensiveBuilding) building).getTarget().isDestroyed()) {
                    ((DefensiveBuilding) building).setTarget(null);// FIXME: 5/5/2018  use attacker setTarget function ArshiaMoghimi
                }
            }
        }
    }

    public void findMovableEffectPostion() {
        for(Troop troop : battleGround.getTroops()) {
            // TODO: 5/6/2018 bfs algorithm which gets a movable and
            // find an arraylist of Locatables with size of speed and set that in movable
        }

    }

    public void performBuildingsEffects() {
        for (Building building : battleGround.getMap().getBuildings()) {
            if(building instanceof DefensiveBuilding) {
                if(!building.isDestroyed() && ((DefensiveBuilding) building).getTarget() != null) {
                    ((DefensiveBuilding) building).giveDamageTo(((DefensiveBuilding) building).getTarget(),
                            battleGround.getMap());
                }
            }

        }
    }

    public void performTroopsEffects() {
        //TODO arshia moghimi
        //don't forget to think about bounties!
    }
}
