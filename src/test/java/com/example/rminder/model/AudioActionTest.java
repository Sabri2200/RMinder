package com.example.rminder.model;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class AudioActionTest {

    @BeforeAll
    public static void setUpClass() throws Exception {
        // Inizializza JavaFX
        Platform.startup(() -> {});
    }

    @Test
    void executeAction() {
        File file = new File("/Users/michelecoscarelli/Downloads/gg.mp3");
        String mediaUrl = file.toURI().toString();
        Media media = new Media(mediaUrl);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        System.out.println("dd");
    }
}