package viewers.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundPlayer {
    private static MediaPlayer backgroundMediaPlayer;
    private static MediaPlayer mediaPlayer;


    public static void playBackground(String sound) {
        if (backgroundMediaPlayer != null) {
            backgroundMediaPlayer.stop();
        }
        backgroundMediaPlayer = new MediaPlayer(new Media(sound));
        backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundMediaPlayer.play();
    }

    public static void play(String sound) {
        mediaPlayer = new MediaPlayer(new Media(sound));
        mediaPlayer.play();
    }
}
