package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ScoreLvl {
    private Integer crossedCar = 0;
    private Integer score = 0;
    private Integer lvl = 1;
    public void render(GraphicsContext gc){
        Font theFont = Font.font( "Ubuntu", FontWeight.BOLD, 28 );
        gc.setFont( theFont );
        gc.fillText( "Score: ", 10, 50 );
        gc.strokeText( "Score: ", 10, 50 );
        gc.fillText(score.toString(), 90, 50);
        gc.strokeText(score.toString(), 90, 50);
        gc.fillText("Level: ", 10, 80);
        gc.strokeText("Level: ", 10,80);
        gc.fillText(lvl.toString(), 90, 80);
        gc.strokeText(lvl.toString(), 90, 80);
    }

    public void increaseScore(){
        score+= lvl;
    }
    public void increaseLvl(){
        lvl++;
    }

    public void increaseCrossedCar(){
        crossedCar++;
        if (crossedCar % 5 == 0){
            increaseLvl();
        }
    }
    public void reset(){
        score = 0;
        lvl = 1;
    }
}
