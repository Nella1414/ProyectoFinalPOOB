package domain.Entities;

import java.awt.*;

public abstract class SupportPlant extends Plant {
    boolean action=false;
    int actionSpeed;

    public SupportPlant(String name,int cost, int life, String type,int actionSpeed, String imagePath, Point position) {
        super(name,cost, life, type, imagePath, position);
        this.actionSpeed = actionSpeed;
    }

    public void action() {
        this.action = true;
    }
}
