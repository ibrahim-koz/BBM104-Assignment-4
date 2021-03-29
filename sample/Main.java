package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;

public class Main extends Application {
    private ArrayList<Car> rivalCars = new ArrayList<>();


    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        primaryStage.setTitle("HUBBM-Racer");
        Scene theScene = new Scene(root, 630, 975);
        primaryStage.setScene(theScene);
        Canvas canvas = new Canvas(630, 975);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        ArrayList<String> input = new ArrayList<>();
        ArrayList<String> lose = new ArrayList<>();
        theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!input.contains(event.getCode().toString())) {
                    input.add(event.getCode().toString());
                }
            }
        });

        theScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                input.remove(event.getCode().toString());
            }
        });
        ScoreLvl scoreLvl = new ScoreLvl();
        Road firstRoad = new Road(0, -487.5);
        Road secondRoad = new Road(0, 0);
        Road thirdRoad = new Road(0, 487.5);
        ArrayList<Road> roads = new ArrayList<>();
        roads.add(firstRoad);
        roads.add(secondRoad);
        roads.add(thirdRoad);
        Car car = new Car(false, 3, 0, 50, 100);
        for (int i = 0; i < 2; i++) {
            rivalCars.add(new Car(true, 0, 18, 50, 100));
        }
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!lose.contains("Q")) {
                    gc.clearRect(0, 0, 630, 975);
                }
                if (input.contains("UP")) {
                    for (Car c : rivalCars) {
                        c.increaseVelocity(roads);
                    }
                } else if (input.contains("DOWN")) {
                    for (Car c : rivalCars) {
                        c.decreaseVelocity(roads);
                    }
                } else if (input.contains("RIGHT")) {
                    car.moveRight();
                } else if (input.contains("LEFT")) {
                    car.moveLeft();
                }
                if (!lose.contains("Q")) {
                    roads.forEach(road -> {
                        road.move();
                        road.render(gc);
                    });
                    car.render(gc);
                    Iterator<Car> iter = rivalCars.iterator();

                    while (rivalCars.get(0).intersects(rivalCars.get(1))) {
                        rivalCars.get(0).reset(true, 0, 18);
                        rivalCars.get(1).reset(true, 0, 18);
                    }
                    while (iter.hasNext()) {
                        Car rivalCar = iter.next();
                        rivalCar.move();
                        rivalCar.render(gc);
                        if (rivalCar.intersects(car)) {
                            lose.add("Q");
                        }
                        if (car.getPositionY() < rivalCar.getPositionY() && !rivalCar.isBehind()) {
                            rivalCar.setBehind(true);
                            scoreLvl.increaseScore();
                            scoreLvl.increaseCrossedCar();
                        }
                    }
                    scoreLvl.render(gc);
                } else {
                    if (input.contains("ENTER")) {
                        lose.remove("Q");
                        reset(roads, car, rivalCars, scoreLvl);
                    }
                }
            }
        }.start();


        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void reset(ArrayList<Road> roads, Car car, ArrayList<Car> rivalCars, ScoreLvl scoreLvl) {
        double y = -487.5;
        for (Road r : roads) {
            r.reset(0, y);
            y += 487.5;
        }

        car.reset(false, 3, 0);

        for (Car c : rivalCars) {
            c.reset(true, 0, 18);
        }
        scoreLvl.reset();
    }
}
