package viewers.utils;

import static viewers.utils.Const.*;

public enum ProgressBarType {
    INFO("blue-bar", INFO_PROGRESS_BAR_WIDTH, INFO_PROGRESS_BAR_HEIGHT),
    ELIXIR_INFO("violet-bar", INFO_PROGRESS_BAR_WIDTH, INFO_PROGRESS_BAR_HEIGHT),
    GOLD_INFO("gold-bar", INFO_PROGRESS_BAR_WIDTH, INFO_PROGRESS_BAR_HEIGHT),
    HIT_POINTS("green-bar", ENTITY_PROGRESS_BAR_WIDTH, ENTITY_PROGRESS_BAR_HEIGHT),
    REMAINED_TIME("green-bar", ENTITY_PROGRESS_BAR_WIDTH, ENTITY_PROGRESS_BAR_HEIGHT),
    INFO_HIT_POINTS("green-bar", INFO_PROGRESS_BAR_WIDTH, INFO_PROGRESS_BAR_HEIGHT);

    private String id;
    private double width;
    private double height;

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
}
