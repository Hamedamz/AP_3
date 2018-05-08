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
    private boolean isGameFinished;

    public BattleGroundGameEngine(BattleGround battleGround) {
        this.battleGround = battleGround;
        isGameFinished = false;
    }

    public void loadBattleGround(BattleGround battleGround) {
        this.battleGround = battleGround;
    }

    public void update() {
        battleGround.setTimeRemaining(battleGround.getTimeRemaining());
        collectBounties();
        battleGround.setNumberOfTroopsDeployed(new int[30][30]);
        battleGround.reset();
        isGameFinished = battleGround.isGameFinished();
        if (isGameFinished) {
            battleGround.endBattle();
        }

    }

    private void collectBounties() {
        ArrayList<Building> buildings = battleGround.getEnemyMap().getBuildings();
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).isDestroyed()) {
                addBounty(buildings.get(i));
            }
        }
    }

    private void addBounty(Building building) {
        Bounty bounty = building.getBounty();
        battleGround.addBounty(bounty);
        Resource myVillageAvailableResourceSpace = battleGround.getMyVillageAvailableResourceSpace();
        battleGround.getVillage().addBounty(bounty);

    }

    public void updateTroopTarget() {
        ArrayList<Destroyable> listOfDefensiveUnits = new ArrayList<>(battleGround.getEnemyMap().getBuildings());
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
        ArrayList<Destroyable> destroyables = new ArrayList<>();
        for (Troop entry : battleGround.getTroops()) {
            destroyables.add((Destroyable) entry);
        }
        for (Building building : battleGround.getEnemyMap().getBuildings()) {
            if (building instanceof DefensiveBuilding) {
                if (((Attacker) building).getTarget() == null || ((Attacker) building).getTarget().isDestroyed()) {
                    try {
                        ((Attacker) building).setTarget(destroyables);
                    } catch (NoTargetFoundException e) {
                        e.getMessage();
                    }
                }
            }
        }
    }



}
