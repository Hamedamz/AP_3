package viewers.utils;

import javafx.scene.image.Image;

import static viewers.utils.Const.*;

public enum ProgressBarType {
    ELIXIR_INFO("violet-bar", INFO_PROGRESS_BAR_WIDTH, INFO_PROGRESS_BAR_HEIGHT, ImageLibrary.ElixirIcon.getImage()),
    GOLD_INFO("gold-bar", INFO_PROGRESS_BAR_WIDTH, INFO_PROGRESS_BAR_HEIGHT, ImageLibrary.GoldIcon.getImage()),
    INFO_HIT_POINTS("green-bar", INFO_PROGRESS_BAR_WIDTH, INFO_PROGRESS_BAR_HEIGHT, ImageLibrary.HitPointsIcon.getImage()),
    HIT_POINTS("green-bar", ENTITY_PROGRESS_BAR_WIDTH, ENTITY_PROGRESS_BAR_HEIGHT),
    REMAINED_TIME("green-bar", ENTITY_PROGRESS_BAR_WIDTH, ENTITY_PROGRESS_BAR_HEIGHT);

    private String id;
    private double width;
    private double height;
    private Image image;

    ProgressBarType(String id, double width, double height, Image image) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    ProgressBarType(String id, double width, double height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Image getImage() {
        return image;
    }
}
