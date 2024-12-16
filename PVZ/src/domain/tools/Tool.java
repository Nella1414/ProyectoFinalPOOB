package domain.tools;
import javax.swing.*;
import java.awt.*;

/**
 * La clase abstracta Tool representa una herramienta en el juego.
 * Define las propiedades y acciones básicas que deben implementar las herramientas específicas.
 */
public abstract class Tool {
    protected String name;

    /**
     * Construye una Tool con el nombre especificado.
     *
     * @param name el nombre de la herramienta
     */
    public Tool(String name) {
        this.name = name;
    }

    /**
     * Devuelve el nombre de la herramienta.
     *
     * @return el nombre de la herramienta
     */
    public String getName() {
        return name;
    }

    /**
     * Realiza la acción de la herramienta en la posición especificada.
     *
     * @param position la posición en la que se realiza la acción
     */
    public void action(Point position) {
        System.out.println("Action");
    }
}