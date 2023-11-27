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
        System.out.println("Eseguit");
        File file = new File(filePath);
        String mediaUrl = file.toURI().toString();
        Media media = new Media(mediaUrl);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    @Override
    public String toString() {
        return "Audio Action";//+"play " + filePath;
    }

}
