package viewers.utils;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class BuildingHolder extends StackPane {
    private ImageView imageView;
    private ProgressBar progressBar;

    public BuildingHolder(ImageView imageView) {
        this.imageView = imageView;
        this.setMaxSize(imageView.getViewport().getWidth(), imageView.getViewport().getHeight());
        this.getChildren().addAll(imageView);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
