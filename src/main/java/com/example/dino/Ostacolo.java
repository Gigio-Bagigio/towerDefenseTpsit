package com.example.dino;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Ostacolo {
    double x, y;
    double vx, vy;
    double radius;
    Color color;

    public Ostacolo(double x, double y, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = 15;
        this.color = Color.CORNFLOWERBLUE;
    }

    public void update() {
        x += vx; // Posizione += velocità
        y += vy;
    }

    public void draw(GraphicsContext gc) {

        gc.drawImage(new Image(getClass().getResourceAsStream("/assets/Asteroids_Foreground.png")), x ,y ,100, 100);
    }
}
