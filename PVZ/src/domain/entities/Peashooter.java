package domain.entities;

import domain.board.Board;

import java.awt.*;

public class Peashooter extends OffensivePlant {

    public Peashooter(Board board, Point position) {
        super("Peashooter", 100, 300, "Offensive", 20, 1.5f, position, "assets/Images/inGame/plants/Peashooter.gif", board); // 1.5 segundos entre ataques
        this.startAttacking(board);
    }

    @Override
    public void attack(Board board) {
        int peashooterRow = board.getRowFromYForPlants(this.getPosition().y);
        Zombie target = board.findClosestZombieInRow(peashooterRow, this.getPosition().x);
        System.out.println("Target: " + target + ", Peashooter row: " + peashooterRow);
        if (target != null) {
            int targetRow = board.getRowFromYForZombies(target.getPosition().y);
            System.out.println("Target row: " + targetRow + ", Peashooter row: " + peashooterRow);
            // Verificar si el zombie está en la misma fila y dentro del rango de ataque del Peashooter
            if (targetRow == peashooterRow) { // Asumiendo que 90 es el rango de ataque
                System.out.println("Peashooter encontró un objetivo en su fila.");
                target.receiveDamage(this.getDamage());
                System.out.println(this.getName() + " atacó a " + target.getName() + " causando " + this.getDamage() + " de daño.");
                if (!target.isAlive()) {
                    board.removeZombie(target.getPosition());
                    System.out.println(target.getName() + " ha sido eliminado.");
                }
            } else {
                System.out.println(this.getName() + " no encontró objetivos dentro del rango.");
            }
        } else {
            System.out.println(this.getName() + " no encontró objetivos en su fila.");
        }
    }
}
