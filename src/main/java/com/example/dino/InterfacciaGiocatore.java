package com.example.dino;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javax.swing.text.html.MinimalHTMLWriter;
import java.util.LinkedList;

public class InterfacciaGiocatore extends Application {

    @Override
    public void start(Stage stage) {

        Canvas canvas = new Canvas(1920, 1080);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Pane root = new Pane(canvas);
        LinkedList<Ostacolo> ostacolos = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            ostacolos.push(new Ostacolo(500+i*550, Math.random() * 10000 % 1080,0, -3));
            ostacolos.push(new Ostacolo(700+i*550, Math.random() * 10000 % 1080,0, 3));
        }
        Scene scene = new Scene(root, 1920, 1080);

        Image img = new Image(getClass().getResourceAsStream("/assets/backGround.png"));

        Torre torreAmica = new Torre(0, 500);

        Player player = new Player(400, 400, 65, 15);

        LinkedList<Impulso> impulsi = new LinkedList<>();
        LinkedList<Impulso> impulsiAttivi = new LinkedList<>();

        LinkedList<ImageView> esplosioni = new LinkedList<>();
        Image gifEsplosione = new Image(getClass().getResourceAsStream("/assets/7BR6qK.gif"));

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

                LinkedList<Impulso> impulsiDaRimuovere = new LinkedList<>();

                for (int i = 0; i < impulsi.size(); i++) {
                    if (impulsi.get(i).x < 1920) {
                        impulsiAttivi.push(impulsi.get(i));
                        impulsi.get(i).update(gc);
                    }
                    for (int j = 0; j < ostacolos.size(); j++) {
                        boolean colpito = ostacolos.get(j).underRock(
                                (int) impulsi.get(i).x,
                                (int) (impulsi.get(i).x + impulsi.get(i).width),
                                (int) impulsi.get(i).y,
                                (int) (impulsi.get(i).y + impulsi.get(i).height)
                        );
                        if (colpito) {
                            double ox = ostacolos.get(j).x;
                            double oy = ostacolos.get(j).y;
                            ostacolos.remove(j);
                            impulsiDaRimuovere.add(impulsi.get(i));

                            // Crea l'ImageView della GIF nella posizione dell'ostacolo
                            ImageView esplosione = new ImageView(gifEsplosione);
                            esplosione.setX(ox);
                            esplosione.setY(oy);
                            esplosione.setFitWidth(100);
                            esplosione.setFitHeight(100);
                            root.getChildren().add(esplosione);
                            esplosioni.add(esplosione);

                            // Rimuovi la GIF dopo 1 secondo
                            PauseTransition pausa = new PauseTransition(Duration.seconds(1));
                            pausa.setOnFinished(e -> {
                                root.getChildren().remove(esplosione);
                                esplosioni.remove(esplosione);
                            });
                            pausa.play();

                            break;
                        }
                    }
                }

                impulsi.removeAll(impulsiDaRimuovere);

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
