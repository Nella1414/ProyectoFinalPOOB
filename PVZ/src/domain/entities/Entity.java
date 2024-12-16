package domain.entities;

import javax.swing.*;
import java.awt.*;

/**
 * La clase Entity representa una entidad genérica en el juego.
 * Contiene propiedades como nombre, costo, posición e ícono.
 */
public class Entity {
    private String name;
    private final int cost;
    private Point position;
    private ImageIcon icon;

    /**
     * Construye una entidad con el nombre, costo, posición y ruta de imagen especificados.
     *
     * @param name el nombre de la entidad
     * @param cost el costo de la entidad
     * @param position la posición de la entidad
     * @param imagePath la ruta al ícono de imagen de la entidad
     */
    public Entity(String name, int cost, Point position, String imagePath) {
        this.name = name;
        this.position = position; // Posición predeterminada, cambiará cuando la planta se coloque en la celda correspondiente
        this.cost = cost;
        this.icon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));
    }

    /**
     * Construye una entidad con el nombre, costo, posición e ícono especificados.
     *
     * @param name el nombre de la entidad
     * @param cost el costo de la entidad
     * @param position la posición de la entidad
     * @param icon el ícono de imagen de la entidad
     */
    public Entity(String name, int cost, Point position, ImageIcon icon) {
        this.name = name;
        this.position = position;
        this.cost = cost;
        this.icon = icon;
    }

    /**
     * Devuelve el nombre de la entidad.
     *
     * @return el nombre de la entidad
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve el costo de la entidad.
     *
     * @return el costo de la entidad
     */
    public int getCost() {
        return cost;
    }

    /**
     * Devuelve la posición de la entidad.
     *
     * @return la posición de la entidad
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Establece la posición de la entidad a las coordenadas especificadas.
     *
     * @param x la coordenada x de la nueva posición
     * @param y la coordenada y de la nueva posición
     */
    public void setPosition(int x, int y) {
        this.position.setLocation(x, y);
    }

    /**
     * Devuelve el ícono de imagen de la entidad.
     *
     * @return el ícono de imagen de la entidad
     */
    public ImageIcon getIcon() {
        return icon;
    }

    /**
     * Establece la posición de la entidad al Point especificado.
     *
     * @param position la nueva posición de la entidad
     */
    public void setPosition(Point position) {
        this.position = position;
    }
}