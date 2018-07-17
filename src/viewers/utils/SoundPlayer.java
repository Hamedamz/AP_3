package viewers.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import models.GameLogic.Entities.Buildings.ElixirMine;
import models.GameLogic.Entities.Buildings.ElixirStorage;
import models.GameLogic.Entities.Buildings.GoldMine;
import models.GameLogic.Entities.Buildings.GoldStorage;
import models.GameLogic.Entities.Entity;
import viewers.AppGUI;

public class SoundPlayer {
    private static SoundPlayer soundPlayer = new SoundPlayer();
    private MediaPlayer backgroundMediaPlayer;
    private MediaPlayer mediaPlayer;

    private SoundPlayer() {
    }

    public static SoundPlayer getInstance() {
        return soundPlayer;
    }


    public void playBackground(String sound) {
        if (backgroundMediaPlayer != null) {
            backgroundMediaPlayer.stop();
        }
        backgroundMediaPlayer = new MediaPlayer(new Media(sound));
        backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundMediaPlayer.play();
    }

    public void play(String sound) {
        mediaPlayer = new MediaPlayer(new Media(sound));
        mediaPlayer.play();
    }

    public static void play(Entity entity) {
        if (entity instanceof GoldStorage || entity instanceof GoldMine) {
            AppGUI.getController().getSoundPlayer().play(Sounds.goldSound);
        }
        else if (entity instanceof ElixirStorage || entity instanceof ElixirMine) {
            AppGUI.getController().getSoundPlayer().play(Sounds.elixirSound);
        }
        else {
            AppGUI.getController().getSoundPlayer().play(Sounds.buildingClickSound);
        }
    }
}
