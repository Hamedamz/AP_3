package viewers;

import models.GameLogic.BattleGround;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Troop.Troop;

import static controllers.OutputFormats.*;
import static controllers.enums.CommandType.TOWERS;
import static controllers.enums.CommandType.TROOPS;

public class BattleGroundViewer extends BasicViewer {
    private BattleGround battleGround;

    public void setBattleGround(BattleGround battleGround) {
        this.battleGround = battleGround;
    }

    public void printStatusResources() {
        printPropertyValue("Gold achieved", battleGround.getLootedBounty().getGold());
        printPropertyValue("Elixir achieved", battleGround.getLootedBounty().getElixir());
        printPropertyValue("Gold remained", battleGround.getRemainingResources().getGold());
        printPropertyValue("Elixir remained", battleGround.getRemainingResources().getElixir());
    }


    public void printStatusUnit() {
        for (String troop : TROOPS) {
            printStatusUnit(troop);
        }
    }

    public void printStatusUnit(String troopType) {
        for (Troop troop : battleGround.getDeployedTroops()) {
            if (troop.getClass().getSimpleName().equals(troopType)) {
                System.out.format(SHOW_STATUS_UNIT_FORMAT, troopType, troop.getLevel(),
                        troop.getPosition().getX(), troop.getPosition().getY(),
                        troop.getLevel());
            }
        }
    }

    public void printStatusTower() {
        for (String tower : TOWERS) {
            printStatusTower(tower);
        }
    }

    public void printStatusTower(String towerType) {
        for (Building building : battleGround.getEnemyMap().getBuildings()) {
            if (building.getClass().getSimpleName().equals(towerType)) {
                System.out.format(SHOW_STATUS_TOWER_FORMAT,
                        towerType, building.getLevel(),
                        building.getPosition().getX(), building.getPosition().getY(),
                        building.getHitPoints());
            }
        }
    }

    public void printStatusAll() {
        printStatusResources();
        printStatusUnit();
        printStatusTower();
    }

    public void printAttackFinishedInfo() {
        System.out.format(ATTACK_FINISHED_INFO_FORMAT,
                battleGround.getLootedBounty().getGold(),
                battleGround.getLootedBounty().getElixir(),
                battleGround.getLootedBounty().getScore());
    }
}
