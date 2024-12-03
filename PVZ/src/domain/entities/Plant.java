package domain.entities;

import domain.board.Board;

import java.awt.*;

public abstract class Plant extends Entity{
    int life;
    String type;

    Board board;

    public Plant(String name,int cost, int life, String type, Point position,String imagePath,Board board) {
        super(name, cost, position, imagePath);
        this.life = life;
        this.type = type;
        this.board = board;
    }

    public int getLife(){return this.life;};
    public String getType(){return this.type;};
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

}