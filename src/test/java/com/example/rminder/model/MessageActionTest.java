/*package com.example.rminder.model;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class MessageActionTest {

    @BeforeEach
    void setUp() {
        // Inizializza JavaFX Environment
        Platform.startup(() -> {});
    }

    @Test
    void executeAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("A Title");
        alert.setHeaderText("A Header");
        alert.setContentText("A Message");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }
}*/