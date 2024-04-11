package com.example.chatudpgrafico;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private TextField txtNombre;

    private HelloApplication application;

    private String nombre;

    public void setApplication(HelloApplication application){
        this.application = application;
    }

    @FXML
    protected void btnAcceptAction() throws IOException {
        nombre = txtNombre.getText();
        Stage stage = (Stage) application.getScene().getWindow();
        application.changeStage(stage, nombre);
    }

    @FXML
    protected void btnCancelAction() {
        application.close();
    }

}