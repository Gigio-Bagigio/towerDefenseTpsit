package com.example.dino;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class NaveAmica {
    double x, y;
    double vx, vy;

    public NaveAmica( double x ,double y,double vy, double vx) {
        this.x = x;
        this.y = y;
        this.vy = vy;
        this.vx = vx;
    }

    public void update(){
        x += vx;
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(new Image(getClass().getResourceAsStream("/assets/naveAmica.png")), x ,y ,100, 100);
    }
}
