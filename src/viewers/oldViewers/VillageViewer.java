package viewers.oldViewers;

import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.GoldStorage;
import models.GameLogic.Resource;
import models.GameLogic.Village;

import static controllers.OutputFormats.*;

public class VillageViewer extends BasicViewer {
    private Village village;

    public VillageViewer(Village village) {
        this.village = village;
    }

    public void printResourcesList() {
        Resource totalResourceStock = village.getTotalResourceStock();
        printPropertyValue("Gold", totalResourceStock.getGold());
        printPropertyValue("Elixir", totalResourceStock.getElixir());
        printPropertyValue("Score", village.getTownHall().getVillageScore());
    }

    public void printMapCells() {
        for (int i = 0; i < village.getGameMap().getMapWidth(); i++) {
            for (int j = 0; j < village.getGameMap().getMapHeight(); j++) {
                if (village.getGameMap().isOccupied(i, j)) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }

    public void printStorageCapacity(Building building) {
        Resource totalResourceStock = village.getTotalResourceStock();
        Resource totalResourceCapacity = village.getTotalResourceCapacity();
        if (building instanceof GoldStorage) {
            System.out.format(STORAGE_CAPACITY_FORMAT, "Gold", totalResourceStock.getGold(), totalResourceCapacity.getGold());
        } else {
            System.out.format(STORAGE_CAPACITY_FORMAT, "Elixir", totalResourceStock.getElixir(), totalResourceCapacity.getElixir());
        }

    }

    public void printCampCapacity() {
        int totalCampTroops = village.getTotalCampTroops();
        int totalCampCapacity = village.getTotalCampCapacity();
        System.out.printf(CAMPS_CAPACITY_FORMAT, totalCampTroops, totalCampCapacity);
    }
}
