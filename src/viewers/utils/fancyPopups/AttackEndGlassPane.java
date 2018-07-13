package viewers.utils.fancyPopups;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import models.GameLogic.Bounty;
import models.GameLogic.Resource;
import viewers.AppGUI;
import viewers.MyVillageScene;
import viewers.utils.*;
import viewers.utils.fancyButtons.RoundButton;

public class AttackEndGlassPane extends GlassPane {

    private RoundButton returnButton;
    private GridPane lootedBountyInfo;

    public AttackEndGlassPane() {
        super("Attack Finished!");
    }

    @Override
    public void setProperties() {
        this.setHeight(Const.WINDOW_HEIGHT);
        this.setWidth(Const.WINDOW_WIDTH);

        lootedBountyInfo = buildLootedBountyInfo();
        returnButton = new RoundButton("Return", "green");

        returnButton.setOnAction(event -> {
            SoundPlayer.play(Sounds.buttonSound);
            MyVillageScene.getInstance().reBuild();
            AppGUI.setStageScene(MyVillageScene.getInstance());
        });

        body.getChildren().clear();
        body.getChildren().addAll(title, lootedBountyInfo, returnButton);
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
        gridPane.add(new StrokeText("score: "), 1, 3);
        gridPane.add(new StrokeText(String.valueOf(lootedBounty.getScore())), 2, 3);
        return gridPane;
    }
}
