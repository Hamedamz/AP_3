package viewers;

import models.GameLogic.Resource;
import models.GameLogic.Village;

public class VillageViewer extends BasicViewer {
    private Village village;

    public VillageViewer(Village village) {
        this.village = village;
    }

    public void printResourcesList() {
        Resource totalResourceStock = village.getTotalResourceStock();
        printPropertyValue("Gold", totalResourceStock.getGold());
        printPropertyValue("Elixir", totalResourceStock.getElixir());
        printPropertyValue("Score", village.getTownHall().getScore());
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
}
