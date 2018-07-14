package models.GameLogic.Entities;

import models.interfaces.Revivable;
import models.interfaces.Updatable;
import models.GameLogic.Position;

public abstract class Defender extends Entity implements Revivable, Updatable {
    public Defender(Position position){
        super(position);
    }

    public Defender() {
    }
}
