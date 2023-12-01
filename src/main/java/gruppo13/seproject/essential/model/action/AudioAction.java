package gruppo13.seproject.essential.model.action;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AudioAction implements Action {
    private String path;

    public AudioAction(String path) {
        this.path = path;
    }

    @Override
    public Boolean execute() {
        File file = new File(path);
        String mediaUrl = file.toURI().toString();
        Media media = new Media(mediaUrl);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.stop();
            System.out.println("L'audio è finito, MediaPlayer fermato.");
        });
        return true;
    }

    @Override
    public ActionType getType() {
        return ActionType.MP3PLAYER;
    }

    @Override
    public String toString() {
        return ActionType.MP3PLAYER.name() + " " + path;
    }

    public String getPath() {
        return path;
    }

    public Boolean setPath() {
        Stage fileChooserDialog = new Stage();
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Audio File Selection");

        FileChooser.ExtensionFilter audioFilter = new FileChooser.ExtensionFilter("File Audio", "*.mp3", "*.wav", "*.aac");
        fileChooser.getExtensionFilters().add(audioFilter);

        File file = fileChooser.showOpenDialog(fileChooserDialog);

        this.path = file.getAbsolutePath();

        return file != null;
    }

}
