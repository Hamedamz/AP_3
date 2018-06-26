package viewers;

import models.GameLogic.GameMap;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.Storage;
import models.GameLogic.Resource;

import java.util.HashMap;

public class MapViewer extends BasicViewer {
    public void printMapInfo(GameMap gameMap) {
        Resource enemyMapResourceStock = getEnemyMapResourceStock(gameMap);
        HashMap<String, Integer> towerTypeNumber = getTowerTypeNumber(gameMap);
        printPropertyValue("Gold", enemyMapResourceStock.getGold());
        printPropertyValue("Elixir", enemyMapResourceStock.getElixir());
        for (java.util.Map.Entry<String, Integer> stringIntegerEntry : towerTypeNumber.entrySet()) {
            printPropertyValue(stringIntegerEntry.getKey(), stringIntegerEntry.getValue());
        }
    }

    private Resource getEnemyMapResourceStock(GameMap gameMap) {
        Resource resource = new Resource(0, 0);
        for (Building building : gameMap.getBuildings()) {
            if (building instanceof Storage) {
                Storage storage = (Storage) building;
                resource.addToThisResource(storage.getStock());
            }
        }
        return resource;
    }

    private HashMap<String , Integer> getTowerTypeNumber(GameMap gameMap) {
        HashMap<String, Integer> towerTypeNumber = new HashMap<>();
        for (Building building : gameMap.getBuildings()) {
            String Building = building.getClass().getSimpleName();
            if (towerTypeNumber.keySet().contains(building.getClass().getSimpleName())) {
                towerTypeNumber.put(Building, towerTypeNumber.get(Building) + 1);
            } else
            towerTypeNumber.put(Building, 1);
        }
        return towerTypeNumber;
    }
}
