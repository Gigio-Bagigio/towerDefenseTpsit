package com.example.dino;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Player {
    double x, y;
    double width, height;
    double radius;
    Color color;

    public Player(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = Color.CORNFLOWERBLUE;
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(new Image(getClass().getResourceAsStream("/assets/navicella.png")), x ,y ,100, 100);
    }



}
