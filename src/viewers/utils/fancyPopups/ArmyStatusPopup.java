package viewers.utils.fancyPopups;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import viewers.AppGUI;
import viewers.utils.Const;
import viewers.utils.TroopsScrollMenu;
import viewers.utils.ButtonActionType;

public class ArmyStatusPopup extends FancyPopup {

    private TroopsScrollMenu troopsScrollMenu;

    public ArmyStatusPopup(Object model) {
        super(model);

        troopsScrollMenu = new TroopsScrollMenu(ButtonActionType.TROOPS, model);
        troopsScrollMenu.setMaxWidth(Const.POPUP_WIDTH - 6 * Const.SPACING);

        GridPane gridPane = new GridPane();
        gridPane.add(troopsScrollMenu, 0,0);
        setBody(gridPane);
        setAnimationTimer(this);
    }

    public static void openPopup(Object model) {
        ArmyStatusPopup armyStatusPopup = new ArmyStatusPopup(model);
        armyStatusPopup.show(AppGUI.getMyVillageScene());
        armyStatusPopup.requestFocus();

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
