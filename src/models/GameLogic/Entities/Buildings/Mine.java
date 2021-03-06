package models.GameLogic.Entities.Buildings;

import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.ID;
import models.setting.GameLogicConfig;

public abstract class Mine extends ResourceBuilding{
    protected int productionRate;

    public Mine(Position position, ID id) {
        super(position, id);
        String className = this.getClass().getSimpleName();
        this.productionRate = (int) GameLogicConfig.getFromDictionary(className + "ProductionRate");
    }

    public abstract Resource produce();

    public int getProductionRate() {
        return productionRate;
    }
}
