package domain.entities;

import domain.board.Board;

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class OffensivePlant extends Plant {
    private Timer attackTimer;
    private int damage;
    private float attackSpeed; // En segundos

    public OffensivePlant(String name, int cost, int life, String type, int damage, float attackSpeed, Point position,String imagePath,Board board) {
        super(name, cost, life, type, position, imagePath,board);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }

    public void startAttacking(Board board) {
        int interval = (int) (attackSpeed * 1000); // Convertir segundos a milisegundos
        attackTimer = new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attack(board);
            }
        });
        attackTimer.start();
    }

    public void stopAttacking() {
        if (attackTimer != null) {
            attackTimer.stop();
        }
    }

    public abstract void attack(Board board); // Metodo definido en subclases

    public int getDamage() {
        return this.damage;
    }

    public float getAttackSpeed() {
        return this.attackSpeed;
    }
}
