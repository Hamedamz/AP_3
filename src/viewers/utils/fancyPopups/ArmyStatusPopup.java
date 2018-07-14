package viewers.utils.fancyPopups;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import viewers.AppGUI;
import viewers.utils.Const;
import viewers.utils.TroopsScrollMenu;
import viewers.utils.ButtonActionType;

public class ArmyStatusPopup extends FancyPopup {

    private TroopsScrollMenu troopsScrollMenu;
    private AnimationTimer animationTimer;

    public ArmyStatusPopup(Object model) {
        super(model);

        troopsScrollMenu = new TroopsScrollMenu(ButtonActionType.TROOPS, model);
        troopsScrollMenu.setMaxWidth(Const.POPUP_WIDTH - 6 * Const.SPACING);

        StackPane body = new StackPane(troopsScrollMenu);
        body.setAlignment(Pos.CENTER);
        setBody(body);
        setAnimationTimer(this);

        this.setOnShown(event -> animationTimer.start());

        this.setOnHidden(event -> animationTimer.stop());
    }

    public static void openPopup(Object model) {
        new ArmyStatusPopup(model).show(AppGUI.getMainStage());
    }

    private void refresh() {
        troopsScrollMenu.refreshForArmyStatus();
    }

    private void setAnimationTimer(ArmyStatusPopup popup) {
        this.animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                popup.refresh();
            }
        };
    }
}
