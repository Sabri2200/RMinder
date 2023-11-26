package com.example.rminder.model;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.nio.file.Paths;

public class AudioAction implements Action {
    private String filePath;

    public AudioAction(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void executeAction() {
        File file = new File("/Users/michelecoscarelli/Downloads/gg.mp3");
        String mediaUrl = file.toURI().toString();
        Media media = new Media(mediaUrl);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    @Override
    public String toString() {
        return "play " + filePath;
    }

}
