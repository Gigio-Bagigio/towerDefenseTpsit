package com.example.dino;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Navicella {
    double x, y;
    double vx, vy;
    double radius;
    Color color;

    public Navicella(double x, double y, double vx, double vy) {
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
        gc.setFill(color);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }
}