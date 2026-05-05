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

import javax.swing.text.html.MinimalHTMLWriter;
import java.util.LinkedList;

public class InterfacciaGiocatore extends Application {

    @Override
    public void start(Stage stage) {

        Canvas canvas = new Canvas(1920, 1080);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        StackPane root = new StackPane(canvas);
        LinkedList<Ostacolo> ostacolos = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            ostacolos.push(new Ostacolo(500+i*550, Math.random() * 10000 % 1080,0, -3));
            ostacolos.push(new Ostacolo(700+i*550, Math.random() * 10000 % 1080,0, 3));
        }

        Image img = new Image(getClass().getResourceAsStream("/assets/spaceBackground.png"));


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                gc.fillRect(0, 0, 1920, 1080); // Cancella tutto
                gc.drawImage(img, 0 ,0 ,1920, 1080);

                for (int i = 0; i < ostacolos.size(); i++) {
                    ostacolos.get(i).update();
                    ostacolos.get(i).draw(gc);
                }


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
