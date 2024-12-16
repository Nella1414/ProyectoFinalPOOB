package domain.tools;

import java.awt.*;

/**
 * La clase Shovel representa una pala en el juego.
 * Extiende la clase Tool y define las propiedades y acciones específicas de una Shovel.
 */
public class Shovel extends Tool {

    /**
     * Construye una Shovel con el nombre especificado.
     */
    public Shovel() {
        super("Shovel");
    }

    /**
     * Realiza la acción de la pala en la posición especificada.
     *
     * @param position la posición en la que se realiza la acción
     */
    @Override
    public void action(Point position) {
        if (position != null) {
            System.out.println("Plant removed at " + position);
        } else {
            System.out.println("Default Shovel action");
        }
    }
}