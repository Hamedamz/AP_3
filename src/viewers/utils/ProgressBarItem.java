package viewers.utils;

import models.interfaces.Destroyable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.GameLogic.Builder;
import models.GameLogic.Entities.Buildings.Storage;
import models.GameLogic.Entities.Troop.Healer;
import models.GameLogic.Resource;
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
                if (model != null) {
                    setMax(((Resource) model).getElixir());
                    setValue(((Resource) model).getElixir());
                } else {
                    setMax(AppGUI.getController().getWorld().getMyVillage().getTotalResourceCapacity().getElixir());
                    setValue(AppGUI.getController().getWorld().getMyVillage().getTotalResourceStock().getElixir());
                }
                break;
            case TOTAL_GOLD_INFO:
                if (model != null) {
                    setMax(((Resource) model).getGold());
                    setValue(((Resource) model).getGold());
                } else {
                    setMax(AppGUI.getController().getWorld().getMyVillage().getTotalResourceCapacity().getGold());
                    setValue(AppGUI.getController().getWorld().getMyVillage().getTotalResourceStock().getGold());
                }
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
                setMax(AppGUI.getController().getWorld().getMyVillage().getTotalCampCapacity());
                setValue(AppGUI.getController().getWorld().getMyVillage().getTotalCampTroops());
                break;
            case HIT_POINTS_INFO:
                setTitle(type.toString());
                setMax(((Destroyable) model).getMaxHitPoints());
                setValue(((Destroyable) model).getHitPoints());
                break;
            case HIT_POINTS:
                if (!model.getClass().equals(Healer.class)) {
                    setMax(((Destroyable) model).getMaxHitPoints());
                    setValue(((Destroyable) model).getHitPoints());
                }
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
        double progress = value / max;
        if (progress >= 0) {
            progressBar.setProgress(progress);
        }
        if (type.equals(ProgressBarType.HIT_POINTS)) {
            if (progress < 0.25) {
                progressBar.setId("red-bar");
            } else if (progress < 0.5) {
                progressBar.setId("orange-bar");
            } else if (progress < 0.75) {
                progressBar.setId("gold-bar");
            } else {
                progressBar.setId("green-bar");
            }
        }
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
