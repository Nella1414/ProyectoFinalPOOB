package domain.entities;

import javax.swing.*;
import java.awt.*;

public class Entity {
    private  String name;
    private final int cost;
    private Point position;
    private ImageIcon icon;


    public Entity(String name, int cost,Point position,String imagePath) {
        this.name = name;
        this.position = position  ;//Poner por defecto este se cambiara cuando se de click en la planta para ubicarla en la celda correspondiente
        this.cost = cost;
        this.icon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));
    }

    public Entity (String name, int cost, Point position, ImageIcon icon){
        this.name = name;
        this.position = position;
        this.cost = cost;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }       //Metodo que retorna el nombre de la entidad

    public int getCost() {
        return cost;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        this.position.setLocation(x, y);
    }

    public ImageIcon getIcon() {
        return icon;
    }
    public void setPosition(Point position) {
        this.position = position;

    }
}
