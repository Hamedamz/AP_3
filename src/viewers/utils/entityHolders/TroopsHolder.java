package viewers.utils.entityHolders;

import javafx.animation.ScaleTransition;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Entities.Troop.AttackerTroop;
import models.GameLogic.Entities.Troop.Healer;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.TrainingTroop;

public class TroopsHolder extends EntityHolder{
    private ScaleTransition scaleTransition = new ScaleTransition();

    public TroopsHolder(Entity entity) {
        super(entity);
        initialize();
    }

    @Override
    public void initialize() {
        super.initialize();

    }

    @Override
    public void refresh() {
        if (entity.getClass().equals(Healer.class)) {
            return;
        }
        if (((AttackerTroop) entity).getHitPoints() < ((AttackerTroop) entity).getMaxHitPoints()) {
            if (!hitPointsProgressBar.isVisible()) {
                hitPointsProgressBar.setVisible(true);
            }
            hitPointsProgressBar.setValues();
        } else if (hitPointsProgressBar.isVisible()){
            hitPointsProgressBar.setVisible(false);
        }
    }

    public boolean isKilled() {
        if (entity.getClass().equals(Healer.class)) {
            return ((Healer) entity).getRemainingTime() == 0;
        }
        return ((AttackerTroop) entity).getHitPoints() == 0;
    }
}
