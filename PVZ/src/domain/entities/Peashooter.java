package domain.entities;

import domain.board.Board;
import presentation.GameEasyWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Peashooter extends OffensivePlant {

    private shoot shoot;
    private Timer shootTimer;

    public Peashooter(int x, int y, Board board, Point position) {
        super(x, y, "Peashooter", 100, 300, "Offensive", 20, 1.5f, position, "assets/Images/inGame/plants/Peashooter.gif", board); // 1.5 segundos entre ataques
        this.shoot = new shoot(x+25, y+10);
        this.startAttacking(board);
        startShooting(board);
    }


    private void startShooting(Board board) {
        shootTimer = new Timer(1500, new ActionListener() { // 1.5 segundos entre disparos
            @Override
            public void actionPerformed(ActionEvent e) {
                shoot newShoot = new shoot(getX() + 25, getY() + 15);
                board.addShoot(newShoot);
            }
        });
        shootTimer.start();
    }

    @Override
    public void attack(Board board) {
        int peashooterRow = board.getRowFromYForPlants(this.getPosition().y);
        Zombie target = board.findClosestZombieInRow(peashooterRow, this.getPosition().x);

        if (target != null && target.isAlive()) {
            int targetRow = board.getRowFromYForZombies(target.getPosition().y);

            if (targetRow == peashooterRow) {
                target.receiveDamage(this.getDamage());
                if (!target.isAlive()) {
                    board.removeZombieList(target);
                }
            }
        }
    }

    public void stopShooting() {
        if (shootTimer != null) {
            shootTimer.stop();
        }
    }

}
