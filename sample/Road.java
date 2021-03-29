package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Road {
    private Image image = new Image("road.png",630,487.5, true,true);
    private double velocityY = 12.5;
    private int positionX;
    private double positionY;
    Random rand = new Random();

    public void reset(int positionX, double positionY){
        velocityY = 12.5;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Road(int positionX, double positionY){
        this.positionX = positionX;
        this.positionY = positionY;
    }
    public void move(){

        positionY += velocityY;

        if (positionY >=975){
            positionY = -487.5+positionY-975;
        }
    }

    public void increaseVelocity(){
        velocityY++;
    }

    public void decreaseVelocity(){
        velocityY--;
    }

    public void render(GraphicsContext gc){
        gc.drawImage(image, positionX, positionY, 630,487.5);
    }
}
