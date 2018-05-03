package viewers;

import models.GameLogic.Resource;
import models.GameLogic.Village;

public class VillageViewer extends BasicViewer {
    private Village village;

    public VillageViewer(Village village) {
        this.village = village;
    }

    private int printResourcesList() {
        Resource totalResourceStock = village.getTotalResourceStock();
        System.out.println("Gold: " + totalResourceStock.getGold());
        System.out.println("Elixir: " + totalResourceStock.getElixir());
        System.out.println("Score: " + village.getTownHall().getScore());
        return 0;
    }
}
