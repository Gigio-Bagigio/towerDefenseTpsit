package com.example.dino;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Player {
    int x, y;
    int width, height;
    double radius;
    Color color;
    double vitaMassima = 100;
    double vitaCorrente = 100;

    public Player(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = Color.CORNFLOWERBLUE;

    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(new Image(getClass().getResourceAsStream("/assets/navicella.png")), x ,y ,100, 100);

        double barraLarghezza = 100;
        double barraAltezza = 5;

        double barraX = x + (100 - barraLarghezza) / 2;
        double barraY = y - 10;

        double percentualeVita = vitaCorrente / vitaMassima;

        if (percentualeVita > 0.5){
            gc.setFill(Color.GREEN);
        } else if (percentualeVita > 0.2){
            gc.setFill(Color.YELLOW);
        } else {
            gc.setFill(Color.RED);
        }

        String testoVita =  (int) (percentualeVita * 100) + "%";
        gc.fillRect(barraX, barraY, barraLarghezza, barraAltezza);
    }

    public void subisciDanno(double danno) {
        this.vitaCorrente -= danno;
        if (this.vitaCorrente < 0) {
            this.vitaCorrente = 0;
        }
    }
    }
