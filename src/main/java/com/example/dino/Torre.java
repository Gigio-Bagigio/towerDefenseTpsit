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
        gc.drawImage(new Image(getClass().getResourceAsStream("/assets/torre.png")), x ,y ,200, 250);
    }

}
