package viewers.utils;

import interfaces.Destroyable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.GameLogic.Builder;
import models.GameLogic.Entities.Buildings.Storage;
import viewers.AppGUI;


public class ProgressBarItem extends Pane {
    private Object model;
    private Text label = new Text();
    private ImageView icon = new ImageView();
    private ProgressBar progressBar = new ProgressBar();
    private ProgressBarType type;
    private String title;
    private HBox content;
    private double value;
    private double max;

    public ProgressBarItem(ProgressBarType type, Object model) {
        this.type = type;
        this.model = model;
        icon.setImage(type.getImage());
        this.content = new HBox(Const.SPACING, this.icon, new VBox(this.label, progressBar));
        this.getChildren().add(content);
        setValues();
        setStyle();
    }

    public ProgressBarItem withTitle(String title) {
        this.title = title;
        return this;
    }

    public void setValues() {
        switch (type) {
            case TOTAL_ELIXIR_INFO:
                setMax(AppGUI.getController().getSinglePlayerWorld().getMyVillage().getTotalResourceCapacity().getElixir());
                setValue(AppGUI.getController().getSinglePlayerWorld().getMyVillage().getTotalResourceStock().getElixir());
                break;
            case TOTAL_GOLD_INFO:
                setMax(AppGUI.getController().getSinglePlayerWorld().getMyVillage().getTotalResourceCapacity().getGold());
                setValue(AppGUI.getController().getSinglePlayerWorld().getMyVillage().getTotalResourceStock().getGold());
                break;
            case ELIXIR_INFO:
                setTitle(type.toString());
                setMax(((Storage) model).getCapacity().getElixir());
                setValue(((Storage) model).getStock().getElixir());
                break;
            case GOLD_INFO:
                setTitle(type.toString());
                setMax(((Storage) model).getCapacity().getGold());
                setValue(((Storage) model).getStock().getGold());
                break;
            case TROOPS_CAPACITY_INFO:
                setTitle(type.toString());
                setMax(AppGUI.getController().getSinglePlayerWorld().getMyVillage().getTotalCampCapacity());
                setValue(AppGUI.getController().getSinglePlayerWorld().getMyVillage().getTotalCampTroops());
                break;
            case HIT_POINTS_INFO:
                setTitle(type.toString());
                setMax(((Destroyable) model).getMaxHitPoints());
                setValue(((Destroyable) model).getHitPoints());
                break;
            case HIT_POINTS:
                setMax(((Destroyable) model).getMaxHitPoints());
                setValue(((Destroyable) model).getHitPoints());
                break;
            case REMAINED_TIME:
                setMax(((Builder) model).getTotalConstructionTime());
                setValue(max - ((Builder) model).getConstructRemainingTime());
                break;
        }

        updateLabel();
        updateProgress();
    }

    private void setStyle() {
        label.setId("progress-bar-label");
        progressBar.setId(type.getId());
        progressBar.setMinWidth(type.getWidth());
        progressBar.setMaxWidth(type.getWidth());
        progressBar.setMinHeight(type.getHeight());
        icon.setFitWidth(Const.PROGRESS_BAR_ICON_SIZE);
        icon.setFitHeight(Const.PROGRESS_BAR_ICON_SIZE);

        switch (type) {
            case HIT_POINTS:
                content.getChildren().remove(icon);
                setLabelVisibility(false);
                break;
            case REMAINED_TIME:
                content.getChildren().remove(icon);
        }
    }

    private void setValue(double value) {
        this.value = value;
    }

    private void setMax(double max) {
        this.max = max;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private void updateProgress() {
        progressBar.setProgress(value / max);
    }

    private void updateLabel() {
        if (title != null) {
            label.setText(title + ": " + String.format("%.0f/%.0f", value, max));
        } else if (type.equals(ProgressBarType.REMAINED_TIME)){
            label.setText(String.format("%.0f", max - value) + "s");
        } else {
            label.setText(String.format("%.0f/%.0f", value, max));
        }
    }

    private void setLabelVisibility(boolean visibility) {
        label.setVisible(visibility);
    }
}
