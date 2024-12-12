package domain.entities;

import domain.board.Board;

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class SupportPlant extends Plant {
    protected int actionSpeed; // Tiempo en milisegundos para realizar su acción
    private Timer timer;

    public SupportPlant(int x, int y, String name, int cost, int life, String type, int actionSpeed, Point position, String imagePath, Board board) {
        super(x, y, name, cost, life, type, position, imagePath, board);
        this.actionSpeed = actionSpeed;
    }

    public void startAction() {
        if (actionSpeed > 0) {
            timer = new Timer(actionSpeed, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    action();
                }
            });
            timer.start();
        }
    }

    public abstract void action(); // Definido por subclases para realizar su acción específica

    public void stopAction() {
        if (timer != null) {
            timer.stop();
        }
    }
}
