package com.example.dino;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class InterfacciaGiocatore extends Application {

    @Override
    public void start(Stage stage) {





        Canvas canvas = new Canvas(1920, 1080);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane(canvas);

        Image img = new Image(getClass().getResourceAsStream("/assets/6GujW+.png"));

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                gc.fillRect(0, 0, 1920, 1080); // Cancella tutto
                gc.drawImage(img, 1920, 1080);
            }
        };
        timer.start();

        stage.setScene(new Scene(root, 1920, 1080));
        stage.setTitle("TowerDefense");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

