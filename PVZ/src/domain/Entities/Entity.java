package domain.Entities;

import javax.swing.*;
import java.awt.*;

public class Entity {
    private final String name;
    private final ImageIcon icon;
    private final int cost;
    private final Point position;

    public Entity(String name, String imagePath, Point position, int cost) {
        this.name = name;
        this.icon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));
        this.position = position;
        this.cost = cost;
    }

    public Entity(String name, ImageIcon icon, Point position, int cost) {
        this.name = name;
        this.icon = icon;
        this.position = position;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public int getCost() {
        return cost;
    }

    public Point getPosition() {
        return position;
    }
}