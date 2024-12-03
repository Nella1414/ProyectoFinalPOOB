package domain.entities;

import domain.board.Board;
import java.awt.*;

public class Peashooter extends OffensivePlant {

    public Peashooter(Board board, Point position) {
        super("Peashooter", 100, 300, "Offensive", 20, 1.5f,position, "assets/Images/inGame/plants/Peashooter.gif",board); // 1.5 segundos entre ataques
        this.startAttacking(board);
    }

    @Override
    public void attack(Board board) {
        Zombie target = board.findClosestZombieInRow(this.getPosition().y, this.getPosition().x);
        if (target != null) {
            target.receiveDamage(this.getDamage());
            System.out.println(this.getName() + " atacó a " + target.getName() + " causando " + this.getDamage() + " de daño.");
            if (!target.isAlive()) {
                board.removeZombie(target.getPosition());
                System.out.println(target.getName() + " ha sido eliminado.");
            }
        } else {
            System.out.println(this.getName() + " no encontró objetivos en su fila.");
        }
    }
}
