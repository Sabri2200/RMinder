//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.rminder.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RMinderController {
    @FXML
    private Label welcomeText;

    public RMinderController() {
    }

    @FXML
    protected void onHelloButtonClick() {
        this.welcomeText.setText("Welcome to RMinder Application!");
    }

    @FXML
    protected void onMessageButtonClick() {
        this.welcomeText.setText("Message Button Clicked del mio patatone bello!!!");
    }
}
