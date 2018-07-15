package viewers.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import models.GameLogic.Entities.Buildings.ElixirMine;
import models.GameLogic.Entities.Buildings.ElixirStorage;
import models.GameLogic.Entities.Buildings.GoldMine;
import models.GameLogic.Entities.Buildings.GoldStorage;
import models.GameLogic.Entities.Entity;

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

    public static void play(Entity entity) {
        if (entity instanceof GoldStorage || entity instanceof GoldMine) {
            SoundPlayer.play(Sounds.goldSound);
        }
        else if (entity instanceof ElixirStorage || entity instanceof ElixirMine) {
            SoundPlayer.play(Sounds.elixirSound);
        }
        else {
            SoundPlayer.play(Sounds.buildingClickSound);
        }
    }
}
