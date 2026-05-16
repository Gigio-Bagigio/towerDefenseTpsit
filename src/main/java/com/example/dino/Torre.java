package com.example.dino;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Torre {
    double x, y;

    public Torre(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void draw(GraphicsContext gc) {

        Image pianeta = new Image(getClass().getResourceAsStream("/assets/pianeta.png"));
        double imgWidth = pianeta.getWidth();
        double imgHeight = pianeta.getHeight();

        gc.drawImage(pianeta, imgWidth * 0.5, 0, imgWidth * 0.5, imgHeight, x, y, 300, 1080);

    }

}
