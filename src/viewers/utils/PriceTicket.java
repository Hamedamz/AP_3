package viewers.utils;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.setting.GameLogicConfig;
import viewers.AppGUI;

public class PriceTicket extends GridPane {

    public static final int SIZE = 25;

    public PriceTicket(String buildingType) {
        ImageView goldIcon = new ImageView(ImageLibrary.GoldIcon.getImage());
        ImageView elixirIcon = new ImageView(ImageLibrary.ElixirIcon.getImage());
        goldIcon.setFitHeight(SIZE);
        goldIcon.setFitWidth(SIZE);
        elixirIcon.setFitHeight(SIZE);
        elixirIcon.setFitWidth(SIZE);
        int gold = GameLogicConfig.getFromDictionary(buildingType + "BuildGold");
        int elixir = GameLogicConfig.getFromDictionary(buildingType + "BuildElixir");
        Text goldLabel = new Text(String.valueOf(gold));
        goldLabel.setId("solid-stroke");
        goldLabel.setFill(Color.WHITE);
        if (gold > AppGUI.getController().getWorld().getMyVillage().getTotalResourceStock().getGold()) {
            goldLabel.setFill(Color.TOMATO);
        }
        Text elixirLabel = new Text(String.valueOf(elixir));
        elixirLabel.setId("solid-stroke");
        elixirLabel.setFill(Color.WHITE);
        if (elixir > AppGUI.getController().getWorld().getMyVillage().getTotalResourceStock().getElixir()) {
            elixirLabel.setFill(Color.TOMATO);
        }

        if (gold != 0) {
            this.add(goldIcon, 0, 0);
            this.add(goldLabel, 1, 0);
        }
        if (elixir != 0) {
            this.add(elixirIcon, 0, 1);
            this.add(elixirLabel, 1, 1);
        }

        this.setVgap(Const.SPACING / 2);
    }
}
