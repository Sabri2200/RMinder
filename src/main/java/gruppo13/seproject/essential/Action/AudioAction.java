package gruppo13.seproject.essential.Action;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class AudioAction implements Action {
    private ActionType type;
    private String path;

    public AudioAction(String path) {
        this.type = ActionType.MP3PLAYER;
        this.path = path;
    }

    @Override
    public void execute() {
        File file = new File(path);
        String mediaUrl = file.toURI().toString();
        Media media = new Media(mediaUrl);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.stop();
            System.out.println("L'audio è finito, MediaPlayer fermato.");
        });
    }

    @Override
    public ActionType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return type.name() + " " + path;
    }

    public String getPath() {
        return path;
    }

}
