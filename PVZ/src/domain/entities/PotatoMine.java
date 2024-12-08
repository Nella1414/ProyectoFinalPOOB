package domain.entities;

import domain.board.Board;

import java.awt.*;

public class PotatoMine extends SupportPlant {
    private boolean isArmed = false;
    public PotatoMine(Board board,Point position) {
        super("PotatoMine", 25, 300, "SupportPlant", 5000,position,"assets/Images/inGame/plants/PotatoMine/PotatoMine.gif",board); // 5000 ms = 5 segundos
        startAction();
    }

    @Override
    public void action() {
        if (!isArmed) {
            isArmed = true;
            System.out.println(this.getName() + " está armada y lista para explotar.");
        }
    }

    public void detonate(Zombie target, Board board) {
        if (isArmed && target != null) {
            System.out.println(this.getName() + " explotó dañando a " + target.getName());
            target.receiveDamage(1000); // Daño masivo
            if (!target.isAlive()) {
                board.getZombies().remove(target.getPosition());
                System.out.println(target.getName() + " ha sido eliminado.");
            }
            board.getPlants().remove(this.getPosition()); // Eliminar la mina tras explotar
            stopAction();
        }
    }

    @Override
    public void attack(Board board) {

    }
}
