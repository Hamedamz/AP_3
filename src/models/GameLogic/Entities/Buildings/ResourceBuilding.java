package models.GameLogic.Entities.Buildings;

import models.GameLogic.Position;
import models.GameLogic.Resource;

public abstract class ResourceBuilding extends Building {
    public ResourceBuilding(Position position, int number) {
        super(position, number);
    }
}
