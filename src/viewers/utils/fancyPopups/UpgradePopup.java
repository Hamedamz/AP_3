package viewers.utils.fancyPopups;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Exceptions.NotEnoughResourcesException;
import models.GameLogic.Exceptions.UpgradeLimitReachedException;
import models.setting.GameLogicConfig;
import viewers.AppGUI;
import viewers.utils.Const;
import viewers.utils.SoundPlayer;
import viewers.utils.Sounds;
import viewers.utils.fancyButtons.RoundButton;

import static controllers.OutputFormats.UPGRADE_CONFIRMATION_FORMAT;

public class UpgradePopup extends FancyPopup {
    private Button confirm;

    private UpgradePopup(Object model) {
        super(model);

        confirm = new RoundButton("Upgrade", "green");
        confirm.setOnAction(event -> {
            try {
                AppGUI.getController().upgradeBuilding((Building) model);
            } catch (UpgradeLimitReachedException | NotEnoughResourcesException e) {
                AppGUI.getMyVillageScene().handleException(e);
            }
            SoundPlayer.play(Sounds.buttonSound);
            this.hide();
        });

        VBox vBox = new VBox(Const.SPACING * 2, new Text(requestUpgradeConfirmation()), confirm);
        vBox.setAlignment(Pos.CENTER);
        setBody(vBox);
    }

    public static void openPopup(Object model) {
        new UpgradePopup(model).show(AppGUI.getMainStage());
    }

    private String requestUpgradeConfirmation() {
        return String.format(UPGRADE_CONFIRMATION_FORMAT,
                ((Building) model).getClass().getSimpleName() + " " + ((Building) model).getID().getCount(),
                GameLogicConfig.getFromDictionary(((Building) model).getClass().getSimpleName() + "UpgradeGold"));

    }
}
