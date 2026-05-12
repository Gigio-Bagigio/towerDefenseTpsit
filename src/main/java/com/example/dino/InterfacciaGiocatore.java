package com.example.dino;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
        for (int i = 0; i < 2; i++) {
            ostacolos.push(new Ostacolo(500+i*550, Math.random() * 10000 % 1080,0, -3));
            ostacolos.push(new Ostacolo(700+i*550, Math.random() * 10000 % 1080,0, 3));
        }
        Scene scene = new Scene(root, 1920, 1080);

        Image img = new Image(getClass().getResourceAsStream("/assets/spaceBackground.png"));

        Torre torreAmica = new Torre(0, 500);

        Player player = new Player(400, 400, 65, 15);

        LinkedList<Impulso> impulsi = new LinkedList<>();
        LinkedList<Impulso> impulsiAttivi = new LinkedList<>();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                gc.fillRect(0, 0, 1920, 1080); // Cancella tutto
                gc.drawImage(img, 0 ,0 ,1920, 1080);

                for (int i = 0; i < ostacolos.size(); i++) {
                    ostacolos.get(i).update();
                    ostacolos.get(i).draw(gc);
                }
                torreAmica.draw(gc);

                for (int i = 0; i < impulsi.size(); i++) {
                    if (impulsi.get(i).x < 1920) {
                        impulsiAttivi.push(impulsi.get(i));
                        impulsi.get(i).update(gc);
                    }
                }
                player.draw(gc);

                for (int i = 0; i < impulsi.size(); i++) {
                    impulsi.pop();
                }
                while (!impulsiAttivi.isEmpty()) {
                    impulsi.push(impulsiAttivi.pop());
                }


            }
        };
        timer.start();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT) {
                    player.x += 20;
                }else if (event.getCode() == KeyCode.LEFT) {
                    player.x -= 20;
                } else if (event.getCode() == KeyCode.UP) {
                    player.y -= 20;
                }else if (event.getCode() == KeyCode.DOWN) {
                    player.y += 20;
                }else if (event.getCode() == KeyCode.SPACE) {
                    impulsi.push(new Impulso(player.x, player.y + 42.5, 65, 65));
                }
            }
        });



        stage.setScene(scene);
        stage.setTitle("TowerDefense");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
