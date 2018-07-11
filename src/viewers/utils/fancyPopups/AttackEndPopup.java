package viewers.utils.fancyPopups;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import models.GameLogic.Bounty;
import models.GameLogic.Resource;
import viewers.AppGUI;
import viewers.MyVillageScene;
import viewers.utils.*;
import viewers.utils.fancyButtons.RoundButton;

public class AttackEndPopup extends StackPane {

    private Text title;
    private RoundButton returnButton;
    private GridPane lootedBountyInfo;
    private VBox body;
    private Rectangle background;

    public AttackEndPopup() {
        background = new Rectangle(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT, Color.BLACK);
        background.setOpacity(0.75);
    }

    public void setProperties() {
        this.setHeight(Const.WINDOW_HEIGHT);
        this.setWidth(Const.WINDOW_WIDTH);

        title = new StrokeText("Attack Finished!");
        lootedBountyInfo = buildLootedBountyInfo();
        returnButton = new RoundButton("Return", "green");

        returnButton.setOnAction(event -> {
            SoundPlayer.play(Sounds.buttonSound);
            AppGUI.setStageScene(MyVillageScene.getInstance());
        });

        body = new VBox(Const.SPACING * 3, title, lootedBountyInfo, returnButton);
        body.setAlignment(Pos.CENTER);
        body.setMinWidth(Const.POPUP_WIDTH);
        body.setMinHeight(Const.WINDOW_HEIGHT);
        body.setLayoutX(Const.WINDOW_WIDTH / 2 - Const.POPUP_WIDTH / 2);
        this.getChildren().clear();
        this.getChildren().addAll(background, body);
    }

    private GridPane buildLootedBountyInfo() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(Const.SPACING);
        gridPane.setVgap(Const.SPACING / 2);
        ImageView goldIcon = new ImageView(ImageLibrary.GoldIcon.getImage());
        ImageView elixirIcon = new ImageView(ImageLibrary.ElixirIcon.getImage());
        goldIcon.setFitWidth(Const.PROGRESS_BAR_ICON_SIZE);
        goldIcon.setFitHeight(Const.PROGRESS_BAR_ICON_SIZE);
        elixirIcon.setFitWidth(Const.PROGRESS_BAR_ICON_SIZE);
        elixirIcon.setFitHeight(Const.PROGRESS_BAR_ICON_SIZE);
        Resource remainingResources = AppGUI.getController().getWorld().getBattleGround().getRemainingResources();
        Bounty lootedBounty = AppGUI.getController().getWorld().getBattleGround().getLootedBounty();
        gridPane.add(new StrokeText("achieved"), 1, 0);
        gridPane.add(new StrokeText("left"), 2, 0);
        gridPane.add(goldIcon, 0, 1);
        gridPane.add(new StrokeText(String.valueOf(lootedBounty.getGold())), 1, 1);
        gridPane.add(new StrokeText(String.valueOf(remainingResources.getGold())), 2, 1);
        gridPane.add(elixirIcon, 0, 2);
        gridPane.add(new StrokeText(String.valueOf(lootedBounty.getElixir())), 1, 2);
        gridPane.add(new StrokeText(String.valueOf(remainingResources.getElixir())), 2, 2);
        return gridPane;
    }
}
