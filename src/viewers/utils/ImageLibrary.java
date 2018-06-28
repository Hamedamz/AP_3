package viewers.utils;

import javax.swing.text.html.ImageView;

public enum ImageLibrary {
    ;
    ImageLibrary(ImageView imageView) {
        this.imageView = imageView;
    }

    private ImageView imageView;

    public ImageView getImageView(){
        return imageView;
    }
}
