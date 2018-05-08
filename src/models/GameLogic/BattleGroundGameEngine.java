package models.GameLogic;

import interfaces.Attacker;
import interfaces.Destroyable;
import interfaces.Effector;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.DefensiveBuilding;
import models.GameLogic.Entities.Defender;
import models.GameLogic.Entities.Troop.AttackerTroop;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.Exceptions.NoTargetFoundException;

import java.util.ArrayList;

public class BattleGroundGameEngine {
    private BattleGround battleGround;
    private boolean isGameFinished;

    public BattleGroundGameEngine() {
        isGameFinished = false;
    }

    public void loadBattleGround(BattleGround battleGround) {
        this.battleGround = battleGround;
    }

    public void update() {
        battleGround.setTimeRemaining(battleGround.getTimeRemaining());
        updateTroopEffectTarget();
        updateDefendersTarget();
        collectBounties();
        findMovablesPath();
        moveMovables();
        removeDestroyedDestroyables();
        performDefendersAttack();
        performTroopsAttack();
        battleGround.reset();
        isGameFinished = battleGround.isGameFinished();
        if (isGameFinished) {
            battleGround.endBattle();
        }

    }

    private void moveMovables() {
        for (int i = 0; i < battleGround.getDeployedTroops().size(); i++) {
            battleGround.getDeployedTroops().get(i).move();
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
        battleGround.getVillage().addBounty(bounty);

    }

    public void updateTroopEffectTarget() {
        ArrayList<Destroyable> listOfDefensiveUnits = new ArrayList<>(battleGround.getEnemyDefenders());
        for (Troop troop : battleGround.getDeployedTroops()) {
            if (((AttackerTroop) troop).getTarget() == null || ((AttackerTroop) troop).getTarget().isDestroyed()) {
                try {
                    ((AttackerTroop) troop).setTarget(listOfDefensiveUnits);
                } catch (NoTargetFoundException e) {
                    isGameFinished = true;
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
            if (defender instanceof Effector) {
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
        for (Defender defender : battleGround.getEnemyDefenders()) {
            if (defender.isDestroyed()) {
                battleGround.getEnemyDefenders().remove(defender);
            }
        }
        for (Troop troop : battleGround.getDeployedTroops()) {
            if (troop.isDestroyed()) {
                battleGround.getDeployedTroops().remove(troop);
            }
        }

    }

    public void performTroopsAttack() {
        for (int i = 0; i < battleGround.getDeployedTroops().size(); i++) {
            ((AttackerTroop) battleGround.getDeployedTroops().get(i)).giveDamageTo(((AttackerTroop) battleGround.getDeployedTroops().get(i)).getTarget(), battleGround);
        }
    }

    public void performDefendersAttack() {
        for (int i = 0; i < battleGround.getEnemyBuildings().size(); i++) {
            if (!battleGround.getEnemyBuildings().get(i).isDestroyed()) {
                if (battleGround.getEnemyBuildings().get(i) instanceof DefensiveBuilding){
                    ((DefensiveBuilding) battleGround.getEnemyBuildings().get(i)).giveDamageTo(((DefensiveBuilding) battleGround.getEnemyBuildings().get(i)).getTarget(), battleGround);

                }
            }
        }
    }

    public void findMovablesPath() {
        for (int i = 0; i < battleGround.getDeployedTroops().size(); i++) {
            battleGround.getDeployedTroops().get(i).findPath(battleGround);
        }
    }

}
