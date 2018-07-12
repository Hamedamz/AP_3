package models.GameLogic;

import interfaces.*;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Defender;
import models.GameLogic.Entities.Troop.AttackerTroop;
import models.GameLogic.Entities.Troop.Healer;
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
        synchronized (battleGround) {
            battleGround.setTimeRemaining(battleGround.getTimeRemaining() - 1);
            updateTroopTarget();
            updateDefendersTarget();
            findMovablesPath();
            moveMovables();
            healTroops();
            performDefendersAttack();
            performTroopsAttack();
            collectBounties();
            handleTimedEvents();
            removeDestroyedDestroyables();
            battleGround.reset();
            isGameFinished = battleGround.isGameFinished();
            if (isGameFinished) {
                battleGround.endBattle();
            }
        }
    }

    private void moveMovables() {
        for (Troop troop : battleGround.getDeployedTroops()) {
            try {
                troop.move();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Defender defender : battleGround.getEnemyDefenders()) {


            if (defender instanceof Movable) {
                ((Movable) defender).move();
            }
        }

    }

    private void collectBounties() {
        synchronized (battleGround) {
            ArrayList<Building> buildings = battleGround.getEnemyBuildings();
            for (int i = 0; i < buildings.size(); i++) {
                if (buildings.get(i).isDestroyed()) {
                    addBounty(buildings.get(i));
                }
            }
        }
    }

    private void addBounty(Building building) {
        synchronized (battleGround) {
            Bounty bounty = building.getBounty();
            battleGround.addBounty(bounty);
        }
    }

    public void updateTroopTarget() {
        ArrayList<Destroyable> listOfDefensiveUnits = new ArrayList<>(battleGround.getEnemyDefenders());

        for (Troop troop : battleGround.getDeployedTroops()) {
            if (troop.getTarget() == null || troop.getTarget().isDestroyed()) {

                try {
                    troop.setTarget(listOfDefensiveUnits);
                } catch (NoTargetFoundException e) {
                    if (troop instanceof AttackerTroop) {
                        isGameFinished = true;
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    public void updateDefendersTarget() {
        ArrayList<Destroyable> destroyables = new ArrayList<>();
        for (Troop entry : battleGround.getDeployedTroops()) {
            if (entry instanceof Destroyable) {
                destroyables.add((Destroyable) entry);
            }
        }
        for (Defender defender : battleGround.getEnemyDefenders()) {
            if (defender instanceof Attacker) {
                if (((Attacker) defender).getTarget() == null || ((Attacker) defender).getTarget().isDestroyed()) {
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
        synchronized (battleGround) {
            for (Defender defender : battleGround.getEnemyDefenders()) {
                if (defender.isDestroyed()) {
                    battleGround.getEnemyBuildings().remove(defender); // FIXME: 5/9/2018
                    defender.destroy();
                }
            }
            for (Troop troop : new ArrayList<>(battleGround.getDeployedTroops())) {
                if (troop.isDestroyed()) {
                    battleGround.getDeployedTroops().remove(troop);
                }
            }
        }
    }

    public void performTroopsAttack() {

        for (Troop troop : battleGround.getDeployedTroops()) {
            if (troop instanceof Attacker) {
                try {
                    ((Attacker) troop).giveDamageTo(troop.getTarget(), battleGround);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void performDefendersAttack() {
        for (Defender defender : battleGround.getEnemyDefenders()) {
            if (!defender.isDestroyed()) {
                if (defender instanceof Attacker) {
                    ((Attacker) defender).giveDamageTo(((Attacker) defender).getTarget(), battleGround);

                }
            }
        }

    }

    public void findMovablesPath() {
        for (Troop troop : battleGround.getDeployedTroops()) {
            troop.findPath(battleGround);
        }
        for (Defender defender : battleGround.getEnemyDefenders()) {
            if (defender instanceof Movable) {
                try {
                    ((Movable) defender).findPath(battleGround);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void healTroops() {
        for (Troop troop : battleGround.getDeployedTroops()) {
            if (troop instanceof Healer) {
                ((Healer) troop).heal(battleGround);
            }
        }

    }

    public void handleTimedEvents() {
        synchronized (battleGround) {
            for (Troop troop : battleGround.getDeployedTroops()) {
                if (troop instanceof TimedEvent) {
                    ((TimedEvent) troop).reduceTime();
                }
            }
        }
    }
}
