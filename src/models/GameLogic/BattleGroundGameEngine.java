package models.GameLogic;

import interfaces.Attacker;
import interfaces.Destroyable;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Defender;
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
        removeDestroyedDestroyables();
        isGameFinished = battleGround.isGameFinished();
        if (isGameFinished) {
            battleGround.endBattle();
        }

    }

    private void collectBounties() {
        ArrayList<Building> buildings = battleGround.getEnemyBuildings();
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
        ArrayList<Destroyable> listOfDefensiveUnits = new ArrayList<>(battleGround.getEnemyDefenders());
        for (Troop troop : battleGround.getDeployedTroops()) {
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

    public void updateDefendersTarget() {
        ArrayList<Destroyable> destroyables = new ArrayList<>();
        for (Troop entry : battleGround.getDeployedTroops()) {
            destroyables.add((Destroyable) entry);
        }
        for (Defender defender : battleGround.getEnemyDefenders()) {
            if (defender instanceof Attacker) {
                if (((Attacker) defender).getTarget() == null || ((Attacker) defender).getTarget().isDestroyed()) { // FIXME: 5/8/2018
                    try {
                        ((Attacker) defender).setTarget(destroyables);
                    } catch (NoTargetFoundException e) {
                        e.getMessage();
                    }
                }
            }
        }
    }

    public void removeDestroyedDestroyables() {
        for(Defender defender : battleGround.getEnemyDefenders()) {
            if(defender.isDestroyed()) {
                battleGround.getEnemyDefenders().remove(defender);
            }
        }
        for (Troop troop: battleGround.getDeployedTroops()) {
            if(troop.isDestroyed()) {
                battleGround.getAllTroops().get(troop.getClass().getSimpleName()).remove(troop);
                battleGround.getDeployedTroops().remove(troop);
            }
        }

    }

}
