package domain.Entities;

import java.awt.*;

public class BasicZombie extends Zombie {

    public BasicZombie(Point position) {
        super("BasicZombie",25, 100, 1, 100, 0.5f, "Básico", "/assets/Images/inGame/zombies/coneheadZombie.gif", position);
    }

    @Override
    public void move() {
        if (!this.getIsfreezed()) {
            System.out.println(this.getType()+ " avanza " + this.getSpeed() + " celda(s).");
        } else {
            System.out.println(this.getType() + " está congelado y no puede moverse.");
        }
    }

    @Override
    public void attack() {
        System.out.println(this.getType() + " ataca causando " + this.getDamage() + " de daño.");
    }
}
