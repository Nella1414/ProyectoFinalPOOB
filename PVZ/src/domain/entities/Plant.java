package domain.entities;

import domain.board.Board;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public abstract class Plant extends Entity {
    int life;
    String type;
    Board board;
    double attackSpeed; // Define attackSpeed
    Timer attackTimer;  // Define attackTimer

    public Plant(String name, int cost, int life, String type, Point position, String imagePath, Board board) {
        super(name, cost, position, imagePath);
        this.life = life;
        this.type = type;
        this.board = board;
    }

    public int getLife() {
        return this.life;
    }

    public String getType() {
        return this.type;
    }

    public void receiveDamage(int damage) {
        this.life -= damage;
        if (this.life < 0) {
            this.life = 0;
        }
        System.out.println(this.getName() + " recibió " + damage + " de daño. Vida restante: " + this.life);
    }

    public boolean isAlive() {
        return this.life > 0;
    }

    public void stopAttacking() {
        if (attackTimer != null) {
            attackTimer.stop();
        }
    }

    public void startAttacking(Board board) {
        int interval = (int) (1000 / attackSpeed);
        attackTimer = new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attack(board);
            }
        });
        attackTimer.start();
    }

    public abstract void attack(Board board); // Define abstract attack method
}