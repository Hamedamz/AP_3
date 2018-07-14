package models.GameLogic;

import models.interfaces.*;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.Wall;
import models.GameLogic.Entities.Defender;
import models.GameLogic.Entities.Troop.Troop;
import models.setting.GameLogicConstants;
import viewers.AppGUI;

import java.util.ArrayList;

public class BattleGroundGameEngine {
    private BattleGround battleGround;

    int battleGroundTurn;

    public BattleGroundGameEngine() {
        battleGroundTurn = 0;
    }

    public void loadBattleGround(BattleGround battleGround) {
        this.battleGround = battleGround;
        battleGround.setWallDestroyed(false);
        battleGround.setGameFinished(false);
    }

    public void update() {
        synchronized (battleGround) {
            battleGroundTurn++;
            if(battleGroundTurn % GameLogicConstants.DEFAULT_TURNS_PER_SEC == 0)
                battleGround.setTimeRemaining(battleGround.getTimeRemaining() - 1);
            updateTroops();
            updateDefenders();
            collectBounties();
            if(battleGroundTurn % GameLogicConstants.DEFAULT_TURNS_PER_SEC == 0)
                handleTimedEvents();
            battleGround.setWallDestroyed(false);
            removeDestroyedDestroyables();
            battleGround.reset();
            if (battleGround.isGameFinished()) {
                battleGround.endBattle();
            }
        }

        AppGUI.getBattleGroundScene().movementHappened();
    }

    private void updateTroops() {
        for(Updatable updatable : battleGround.getDeployedTroops()) {
            updatable.update(battleGround, GameLogicConstants.DEFAULT_TURNS_PER_SEC, battleGroundTurn);
        }
    }

    private void updateDefenders() {
        for (Updatable updatable : battleGround.getEnemyDefenders()) {
            updatable.update(battleGround, GameLogicConstants.DEFAULT_TURNS_PER_SEC, battleGroundTurn);
        }
    }


    private void addBounty(Building building) {
            Bounty bounty = building.getBounty();
            battleGround.addBounty(bounty);
    }

    //    private void moveMovables() {
//        for (Troop troop : battleGround.getDeployedTroops()) {
//            troop.move();
//        }
//        for (Defender defender : battleGround.getEnemyDefenders()) {
//            if (defender instanceof Movable) {
//                ((Movable) defender).move();
//            }
//        }
//    }

    private void collectBounties() {
        ArrayList<Building> buildings = battleGround.getEnemyBuildings();
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).isDestroyed()) {
                addBounty(buildings.get(i));
            }
        }

    }

//    public void updateTroopTarget() {
//        ArrayList<Destroyable> listOfDefensiveUnits = new ArrayList<>(battleGround.getEnemyDefenders());
//        ArrayList<Destroyable> listOfDeployedDestroyables = new ArrayList<>();
//        for(Troop troop : battleGround.getDeployedTroops()) {
//            if(troop instanceof Destroyable)
//            listOfDeployedDestroyables.add((Destroyable) troop);
//        }
//        for (Troop troop : battleGround.getDeployedTroops()) {
//            if (troop.getTarget() == null || troop.getTarget().isDestroyed() || isWallDestroyed) {
//                try {
//                    if(troop instanceof AttackerTroop) {
//                        troop.findTarget(listOfDefensiveUnits);
//                    } else if (troop instanceof Healer) {
//                        troop.findTarget(listOfDeployedDestroyables);
//                    }
//                } catch (NoTargetFoundException e) {
//                    if (troop instanceof AttackerTroop) {
//                        isGameFinished = true;
//                    }
//                }
//            }
//
//        }
//
//    }

//    public void updateDefendersTarget() {
//        ArrayList<Destroyable> destroyables = new ArrayList<>();
//        for (Troop entry : battleGround.getDeployedTroops()) {
//            if (entry instanceof Destroyable) {
//                destroyables.add((Destroyable) entry);
//            }
//        }
//        for (Defender defender : battleGround.getEnemyDefenders()) {
//            if (defender instanceof Attacker) {
//                if (((Attacker) defender).getTarget() == null || ((Attacker) defender).getTarget().isDestroyed()) {
//                    try {
//                        ((Attacker) defender).findTarget(destroyables);
//                    } catch (NoTargetFoundException e) {
//                        e.getMessage();
//                    }
//                }
//            }
//        }
//    }

    public void removeDestroyedDestroyables() {
        for (Defender defender : battleGround.getEnemyDefenders()) {
            if (defender.isDestroyed()) {
                if(defender instanceof Wall) battleGround.setWallDestroyed(true);
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

//    public void performTroopsAttack() {
//        for (Troop troop : battleGround.getDeployedTroops()) {
//            if (troop instanceof Attacker) {
//                ((Attacker) troop).giveDamageTo(troop.getTarget(), battleGround);
//            }
//        }
//    }
//
//    public void performDefendersAttack() {
//        for (Defender defender : battleGround.getEnemyDefenders()) {
//            if (!defender.isDestroyed()) {
//                if (defender instanceof Attacker) {
//                    ((Attacker) defender).giveDamageTo(((Attacker) defender).getTarget(), battleGround);
//                }
//            }
//        }
//    }
//
//    public void findMovablesPath() {
//        for (Troop troop : battleGround.getDeployedTroops()) {
//            troop.findPath(battleGround);
//        }
//        for (Defender defender : battleGround.getEnemyDefenders()) {
//            if (defender instanceof Movable) {
//                ((Movable) defender).findPath(battleGround);
//            }
//        }
//
//    }
//
//    public void healTroops() {
//        for (Troop troop : battleGround.getDeployedTroops()) {
//            if (troop instanceof Healer) {
//                ((Healer) troop).heal(battleGround);
//            }
//        }
//
//    }

    public void handleTimedEvents() {
        for (Troop troop : battleGround.getDeployedTroops()) {
            if (troop instanceof TimedEvent) {
                ((TimedEvent) troop).reduceTime();
            }
        }

    }
}
