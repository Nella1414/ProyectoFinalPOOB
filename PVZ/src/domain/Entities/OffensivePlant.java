package domain.Entities;

import java.awt.*;

public abstract class OffensivePlant extends Plant {
    int damage;
   float attackSpeed;

    public OffensivePlant(String name,int cost, int life, String type, int damage, float attackSpeed, String imagePath, Point position) {
        super(name,cost, life, type, imagePath, position);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }

    public void attack() {
    }

    public int getDamage() {
        return this.damage;
    }

    public float getAttackSpeed() {
        return this.attackSpeed;
    }

}
