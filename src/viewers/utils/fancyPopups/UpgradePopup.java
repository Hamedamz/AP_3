package viewers.utils.fancyPopups;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.Camp;
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
        confirm.setOnAction(event -> actionButton());
        confirm.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                actionButton();
            }
        });

        Text text = new Text(requestUpgradeConfirmation());
        text.setFill(Color.WHITE);
        VBox vBox = new VBox(Const.SPACING * 2, text, confirm);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefWidth(Const.POPUP_WIDTH);
        GridPane gridPane = new GridPane();
        gridPane.add(vBox, 0, 0);
        setBody(gridPane);
    }

    public static void openPopup(Object model) {
        if (model instanceof Camp) {
            return;
        }
        UpgradePopup upgradePopup = new UpgradePopup(model);
        upgradePopup.show(AppGUI.getMyVillageScene());
        upgradePopup.confirm.requestFocus();

    }

    private String requestUpgradeConfirmation() {
        return String.format(UPGRADE_CONFIRMATION_FORMAT,
                ((Building) model).getClass().getSimpleName() + " " + ((Building) model).getID().getCount(),
                GameLogicConfig.getFromDictionary(((Building) model).getClass().getSimpleName() + "UpgradeGold"));

    }

    private void actionButton() {
        try {
            AppGUI.getController().upgradeBuilding((Building) model);
        } catch (UpgradeLimitReachedException | NotEnoughResourcesException e) {
            AppGUI.getMyVillageScene().handleException(e);
        }
        AppGUI.getController().getSoundPlayer().play(Sounds.buttonSound);
        this.hide();
    }
}
