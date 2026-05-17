package com.example.dino;

import com.sun.source.tree.WhileLoopTree;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import javax.swing.plaf.IconUIResource;
import java.util.LinkedList;

public class InterfacciaGiocatore extends Application {
    final boolean[] giocoFinito = {false};
    private int contatoreFrame = 0;
    private int roccieAbbattute = 0;

    @Override
    public void start(Stage stage) {

        Canvas canvas = new Canvas(1920, 1080);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Pane root = new Pane(canvas);
        LinkedList<Ostacolo> ostacolos = new LinkedList<>();
        LinkedList<NaveAmica> naveAmicas = new LinkedList<>();

        for (int i = 0; i < 5; i++) {
            ostacolos.push(new Ostacolo(1920, Math.random() * 10000 % 1080, -3, 0));
        }
        Scene scene = new Scene(root, 1920, 1080);

        Image img = new Image(getClass().getResourceAsStream("/assets/sfondo.png"));

        Terra torreAmica = new Terra(0, 0);

        Player player = new Player(400, 400, 65, 15);

        LinkedList<Impulso> impulsi = new LinkedList<>();
        LinkedList<Impulso> impulsiAttivi = new LinkedList<>();

        LinkedList<ImageView> esplosioni = new LinkedList<>();
        Image gifEsplosione = new Image(getClass().getResourceAsStream("/assets/7BR6qK.gif"));


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                gc.fillRect(0, 0, 1920, 1080); // Cancella tutto
                gc.drawImage(img, 0, 0, 1920, 1080);

                contatoreFrame++;
                if (contatoreFrame >= 540) {
                    double yCasuale = 0;
                    boolean posizioneInvalida = true;
                    int tentativiMassimi = 20;
                    int tentativi = 0;
                    while (posizioneInvalida && tentativi < tentativiMassimi) {
                        yCasuale = Math.random() * (1080 - 100);
                        posizioneInvalida = false;
                        tentativi++;
                        for (int i = 0; i < ostacolos.size(); i++) {
                            Ostacolo roccia = ostacolos.get(i);

                            if (roccia.x > 1700) {
                                if (yCasuale + 100 > roccia.y && yCasuale < roccia.y + 100) {
                                    posizioneInvalida = true;
                                    break;
                                }
                            }
                        }
                    }

                    naveAmicas.push(new NaveAmica(1920, yCasuale, 0, -3));
                    contatoreFrame = 0;
                }


                for (int i = 0; i < ostacolos.size(); i++) {
                    ostacolos.get(i).update();
                    ostacolos.get(i).draw(gc);
                }

                for (int i = 0; i < naveAmicas.size(); i++) {
                    naveAmicas.get(i).update();
                    naveAmicas.get(i).draw(gc);
                }
                torreAmica.draw(gc);

                LinkedList<Impulso> impulsiDaRimuovere = new LinkedList<>();


                // controlla le collisoni degli impulsi con le rocce
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
                            roccieAbbattute++;
                            double ox = ostacolos.get(j).x;
                            double oy = ostacolos.get(j).y;
                            ostacolos.remove(j);
                            impulsiDaRimuovere.add(impulsi.get(i));

                            ImageView esplosione = new ImageView(gifEsplosione);
                            esplosione.setX(ox);
                            esplosione.setY(oy);
                            esplosione.setFitWidth(100);
                            esplosione.setFitHeight(100);
                            root.getChildren().add(esplosione);
                            esplosioni.add(esplosione);

                            PauseTransition pausa = new PauseTransition(Duration.seconds(1));
                            pausa.setOnFinished(e -> {
                                root.getChildren().remove(esplosione);
                                esplosioni.remove(esplosione);
                            });
                            pausa.play();

                            break;
                        } else if (ostacolos.get(j).x < 0) {
                            ostacolos.remove(j);
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

                while (ostacolos.size() < 4) {
                    ostacolos.push(new Ostacolo(1920, Math.random() * 10000 % 1080, -3, 0));
                }

                for (int i = 0; i < ostacolos.size(); i++) {
                    if (ostacolos.get(i).x <= 300) {
                        torreAmica.subisciDanno(10);
                        ostacolos.remove(i);
                        i--;
                    }
                }
                for (int i = 0; i < naveAmicas.size(); i++) {
                    if (naveAmicas.get(i).x <= 300) {
                        torreAmica.autoHeal();
                        naveAmicas.remove(i);
                        i--;
                    }
                }
                for (int i = 0; i < ostacolos.size(); i++) {
                    boolean navicellaColpita = ostacolos.get(i).underRock(player.x, player.width + player.x,  player.y,  player.height + player.y);
                    if (navicellaColpita) {
                        player.subisciDanno(70);
                        ostacolos.remove(i);
                        i--;
                    }
                }
                if (torreAmica.vitaCorrente <= 0){
                    giocoFinito[0] = true;
                    gc.setFill(Color.BLACK);
                    gc.fillRect(0, 0, 1920, 1080);

                    gc.setFill(Color.RED);
                    gc.setFont(new javafx.scene.text.Font("Arial", 80));
                    gc.setTextAlign(TextAlignment.CENTER);
                    gc.fillText("GAME OVER", 1920 / 2.0, 1080 / 2.0);

                    gc.setFill(Color.WHITE);
                    gc.setFont(new javafx.scene.text.Font("Arial", 30));
                    gc.fillText("La Terra è stata distrutta!", 1920 / 2.0, (1080 / 2.0) + 60);
                    return;
                }
                if (player.vitaCorrente <= 0){
                    giocoFinito[0] = true;
                    giocoFinito[0] = true;
                    gc.setFill(Color.BLACK);
                    gc.fillRect(0, 0, 1920, 1080);

                    gc.setFill(Color.RED);
                    gc.setFont(new javafx.scene.text.Font("Arial", 80));
                    gc.setTextAlign(TextAlignment.CENTER);
                    gc.fillText("GAME OVER", 1920 / 2.0, 1080 / 2.0);

                    gc.setFill(Color.WHITE);
                    gc.setFont(new javafx.scene.text.Font("Arial", 30));
                    gc.fillText("La tua navicella è stata distrutta!", 1920 / 2.0, (1080 / 2.0) + 60);
                    return;
                }
                if (roccieAbbattute > 10) {
                    giocoFinito[0] = true;
                    giocoFinito[0] = true;
                    gc.setFill(Color.BLACK);
                    gc.fillRect(0, 0, 1920, 1080);

                    gc.setFill(Color.GOLD);
                    gc.setFont(new javafx.scene.text.Font("Arial", 80));
                    gc.setTextAlign(TextAlignment.CENTER);
                    gc.fillText("WIN", 1920 / 2.0, 1080 / 2.0);

                    gc.setFill(Color.WHITE);
                    gc.setFont(new javafx.scene.text.Font("Arial", 30));
                    gc.fillText("La Terra è salva!", 1920 / 2.0, (1080 / 2.0) + 60);
                    stop();
                }

            }
        };
        timer.start();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.D) {
                    player.x += 20;
                }else if (event.getCode() == KeyCode.A) {
                    player.x -= 20;
                } else if (event.getCode() == KeyCode.W) {
                    player.y -= 20;
                }else if (event.getCode() == KeyCode.S) {
                    player.y += 20;
                }else if (event.getCode() == KeyCode.SPACE) {
                    if (impulsi.size() < 5) {
                        impulsi.push(new Impulso(player.x, player.y + 42.5, 65, 65));
                    }
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
