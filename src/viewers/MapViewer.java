package viewers;

import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.Storage;
import models.GameLogic.Map;
import models.GameLogic.Resource;

import java.util.HashMap;

public class MapViewer extends BasicViewer {
    public void printMapInfo(Map map) {
        Resource enemyMapResourceStock = getEnemyMapResourceStock(map);
        HashMap<String, Integer> towerTypeNumber = getTowerTypeNumber(map);
        printPropertyValue("Gold", enemyMapResourceStock.getGold());
        printPropertyValue("Elixir", enemyMapResourceStock.getElixir());
        for (java.util.Map.Entry<String, Integer> stringIntegerEntry : towerTypeNumber.entrySet()) {
            printPropertyValue(stringIntegerEntry.getKey(), stringIntegerEntry.getValue());
        }
    }

    private Resource getEnemyMapResourceStock(Map map) {
        Resource resource = new Resource(0, 0);
        for (Building building : map.getBuildings()) {
            if (building instanceof Storage) {
                Storage storage = (Storage) building;
                resource.addToThisResource(storage.getStock());
            }
        }
        return resource;
    }

    private HashMap<String , Integer> getTowerTypeNumber(Map map) {
        HashMap<String, Integer> towerTypeNumber = new HashMap<>();
        for (Building building : map.getBuildings()) {
            String Building = building.getClass().getSimpleName();
            if (towerTypeNumber.keySet().contains(building.getClass().getSimpleName())) {
                towerTypeNumber.put(Building, towerTypeNumber.get(Building) + 1);
            } else
            towerTypeNumber.put(Building, 1);
        }
        return towerTypeNumber;
    }
}
