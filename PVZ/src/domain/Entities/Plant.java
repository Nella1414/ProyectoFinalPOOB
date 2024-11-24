package domain.Entities;

import java.awt.*;

public abstract class Plant extends Entity{
    int life;
    String type;

    public Plant(String name,int cost, int life, String type, String imagePath, Point position) {
        super(name, imagePath, position, cost);
        this.life = life;
        this.type = type;
    }

    public int getLife(){return this.life;};
    public String getType(){return this.type;};
    public void receiveDamage(int damage){
        this.life -= damage;
    }
    public boolean isAlive(){
        if(this.life > 0){
            return true;
        }
        return false;}
}