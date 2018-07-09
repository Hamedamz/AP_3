package models.GameLogic.Entities.Buildings;

import models.GameLogic.Position;
import models.GameLogic.ID;

public abstract class ResourceBuilding extends Building {
    public ResourceBuilding(Position position, ID id) {
        super(position, id);
    }
}
