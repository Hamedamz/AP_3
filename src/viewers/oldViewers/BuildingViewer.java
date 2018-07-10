package viewers.oldViewers;

import controllers.Exceptions.InvalidInputException;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.DefensiveBuilding;
import models.setting.GameLogicConfig;

import static controllers.InputFormats.POSITION_FORMAT;
import static controllers.OutputFormats.*;

public class BuildingViewer extends BasicViewer{
    public void printOverallInfo(Building building) {
        printPropertyValue("Level", building.getLevel());
        printPropertyValue("Health", building.getHitPoints());
    }

    public void printUpgradeInfo(Building building) {
        printPropertyValue("Upgrade Cost", GameLogicConfig.getFromDictionary(building.getClass().getSimpleName() + "UpgradeGold"));
    }


    public void requestBuildConfirmation(String buildingType) {
        System.out.format(BUILD_CONFIRMATION_FORMAT, buildingType,
                GameLogicConfig.getFromDictionary(buildingType + "BuildGold"),
                GameLogicConfig.getFromDictionary(buildingType + "BuildElixir"));
    }

    public void requestPositionToBuild(String buildingType) {
        System.out.format(CHOOSE_POSITION_TO_BUILD_FORMAT, buildingType);
    }

    public String getPositionToBuild() throws InvalidInputException {
        String position = getInput();
        if (position.matches(POSITION_FORMAT)) {
            return position;
        } else throw new InvalidInputException("invalid input");
    }

    public void printAttackInfo(DefensiveBuilding building) {
        printPropertyValue("Target", building.getTargetType().toString());
        printPropertyValue("Damage", building.getDamage());
        printPropertyValue("Range", building.getMapRange());
    }

    public void printTargetInfo(DefensiveBuilding model) {
        System.out.printf("this building attacks %s with %s damage\n",model.getTargetType().toString(), model.getDamageType().toString());
    }
}
