package com.example.chatudpgrafico;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private Scene scene;
    private Stage primaryStage;

    public Scene getScene(){
        return scene;
    }

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        scene = new Scene(fxmlLoader.load(), 400, 170);
        stage.setTitle("ChatUDP");

        HelloController helloController = fxmlLoader.getController();

        helloController.setApplication(this);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void changeStage(Stage stage, String nombre) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("chat-view.fxml"));
        scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("ChatUDP");

        ChatViewController chatViewController = fxmlLoader.getController();
        chatViewController.setName(nombre);
        chatViewController.setApplication(this);
        stage.setScene(scene);
        stage.show();
    }

    public void close(){
        primaryStage.close();
    }
}