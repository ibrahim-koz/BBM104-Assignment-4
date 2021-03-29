package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

public class Car {
    private boolean isBehind = false;
    private Image image;
    private int velocityX;
    private int velocityY;
    private int positionX;
    private int positionY;
    private int width;
    private int height;
    Random rand = new Random();

    public Car(boolean isRival, int velocityX, int velocityY, int width, int height) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.width = width;
        this.height = height;
        if (!isRival) {
            positionX = 600 / 2 - width / 2;
            setPositionY(960 - height);
            image = new Image("car.png", width, height, true, true);
        } else {
            positionX = rand.nextInt(376) + 100;
            setPositionY(-rand.nextInt(200) - 300);
            image = new Image("rival.png", width, height, true, true);
        }

    }

    public void reset(boolean isRival, int velocityX, int velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        if (!isRival) {
            positionX = 600 / 2 - width / 2;
            setPositionY(960 - height);
        } else {
            positionX = rand.nextInt(376) + 100;
            setPositionY(-rand.nextInt(200) - 300);
        }
    }

    public void move() {
        setPositionY(getPositionY() + velocityY);

        if (getPositionY() > 975) {
            setBehind(false);
            positionX = rand.nextInt(376) + 100;
            setPositionY(-rand.nextInt(200) - 300);
        }
    }

    public void increaseVelocity(ArrayList<Road> roads) {
        if (velocityY < 30) {
            velocityY++;

            for (Road road : roads) {
                road.increaseVelocity();
            }


        }
    }

    public void decreaseVelocity(ArrayList<Road> roads) {
        if (velocityY > 18) {
            velocityY--;

            for (Road road : roads) {
                road.decreaseVelocity();
            }

        }
    }

    public void moveRight() {
        if (positionX < 480)
            positionX += 5;
    }

    public void moveLeft() {
        if (positionX > 105)
            positionX -= 5;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, positionX, getPositionY());
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, getPositionY(), width, height);
    }

    public boolean intersects(Car c) {
        return c.getBoundary().intersects(this.getBoundary());
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public boolean isBehind() {
        return isBehind;
    }

    public void setBehind(boolean behind) {
        isBehind = behind;
    }
}
