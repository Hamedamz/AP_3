package viewers;

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
        printPropertyValue("Score", village.getTownHall().getTownHallScore());
    }

    public void printMapCells() {
        for (int i = 0; i < village.getMap().getWidth(); i++) {
            for (int j = 0; j < village.getMap().getHeight(); j++) {
                if (village.getMap().isOccupied(i, j) || i == 0 || j == 0 || i == village.getMap().getWidth() - 1 || j == village.getMap().getHeight() - 1) {
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
            System.out.format(SOURCES_INFO_FORMAT, "Gold", totalResourceStock.getGold(), totalResourceCapacity.getGold());
        } else {
            System.out.format(SOURCES_INFO_FORMAT, "Elixir", totalResourceStock.getElixir(), totalResourceCapacity.getElixir());
        }

    }
}
