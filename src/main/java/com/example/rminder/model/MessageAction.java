package com.example.rminder.model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MessageAction implements Action{
    private String title, message;

    public MessageAction(String title, String message) {
        this.title = title;
        this.message = message;
    }

    @Override
    public void executeAction() {
        System.out.println("Eseguit" + this.message);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(this.title);
        alert.setContentText(this.message);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }
    @Override
    public String toString() {
        return "Message Action";
    }
}
