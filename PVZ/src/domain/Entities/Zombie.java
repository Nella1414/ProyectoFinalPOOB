package domain.Entities;

import java.awt.*;

public abstract class Zombie extends Entity {
    private int life;
    private int speed;
    private int damage;
    private float attackSpeed;
    private String type;
    private boolean isfreezed;

    public Zombie(String name, int cost, int life, int speed, int damage, float attackSpeed, String type, String imagePath,
            Point position) {
        super(name, imagePath, position, cost);
        this.life = life;
        this.speed = speed;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.type = type;
        this.isfreezed = false;
    }

    public abstract void move();

    public abstract void attack();

    public void receiveDamage(int damage) {
        this.life -= damage;
        System.out.println(type + " recibe " + damage + " de daño. Vida restante: " + life);
    }

    public boolean isAlive() {
        return this.life > 0;
    }

    public void freeze() {
        this.isfreezed = true;
        System.out.println(type + " está congelado.");
    }

    public void unfreeze() {
        this.isfreezed = false;
        System.out.println(type + " ya no está congelado.");
    }

    // Getters
    public int getLife() {
        return life;
    }


    public int getSpeed() {
        return speed;
    }

    public int getDamage() {
        return damage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public String getType() {
        return type;
    }

    public boolean getIsfreezed() {
        return this.isfreezed;
    }
}
