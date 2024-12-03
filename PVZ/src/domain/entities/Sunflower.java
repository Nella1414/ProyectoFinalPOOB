package domain.entities;

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import domain.board.Board;

public class Sunflower extends SupportPlant {

    public Sunflower(Board board, Point position) {
        super("Sunflower", 50, 300, "SupportPlant", 20000, position, "assets/Images/inGame/plants/SunFlower.gif",board); // 20 segundos = 20000 ms
        this.startAction();
    }

    @Override
    public void action() {
        if (board != null) {
            board.addSun(25);
        System.out.println(this.getName() + " ha generado 25 soles.");
        }
    }
}
