package viewers.utils.entityHolders;

import javafx.animation.ScaleTransition;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Entities.Troop.AttackerTroop;

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
        if (((AttackerTroop) entity).getHitPoints() < ((AttackerTroop) entity).getMaxHitPoints()) {
            if (!hitPointsProgressBar.isVisible()) {
                hitPointsProgressBar.setVisible(true);
            }
            hitPointsProgressBar.setValues();
        } else if (hitPointsProgressBar.isVisible()){
            hitPointsProgressBar.setVisible(false);
        }
    }
}
