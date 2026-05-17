package com.example.dino;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.lang.model.util.ElementScanner6;

public class Terra {
    double x, y;
    double vitaMassima = 100;
    double vitaCorrente = 100;

    public Terra(double x, double y) {
        this.x = x;
        this.y = y;


    }

    public void draw(GraphicsContext gc) {

        Image pianeta = new Image(getClass().getResourceAsStream("/assets/pianeta.png"));
        double imgWidth = pianeta.getWidth();
        double imgHeight = pianeta.getHeight();

        gc.drawImage(pianeta, imgWidth * 0.5, 0, imgWidth * 0.5, imgHeight, x, y, 300, 1080);

        double barraLarghezza = 500;
        double barraAltezza = 50;

        double barraX =  (1920 - barraLarghezza) / 2;
        double barraY = 30;

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
        gc.fillText(testoVita, 1920 / 2, barraY - 4);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeRect(barraX, barraY, barraLarghezza, barraAltezza);
    }

    public void subisciDanno(double danno) {
        this.vitaCorrente -= danno;
        if (this.vitaCorrente < 0) {
            this.vitaCorrente = 0;
        }
    }
}
