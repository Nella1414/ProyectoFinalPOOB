package domain;

import javax.swing.*;
import java.awt.*;

public class Entity {
    private final String name;
    private final ImageIcon icon;
    private final Point position;

    public Entity(String name, String imagePath, Point position) {
        this.name = name;
        this.icon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public Point getPosition() {
        return position;
    }
}