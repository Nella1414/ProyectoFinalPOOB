package domain.entities;

import domain.board.Board;

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La clase SupportPlant representa una planta de apoyo en el juego.
 * Extiende la clase Plant y define las propiedades y acciones comunes a todas las plantas de apoyo.
 */
public abstract class SupportPlant extends Plant {
    protected int actionSpeed; // Tiempo en milisegundos para realizar su acción
    private Timer timer;

    /**
     * Construye una SupportPlant con las coordenadas, nombre, costo, vida, tipo, velocidad de acción, posición, ruta de imagen y tablero especificados.
     *
     * @param x la coordenada x de la planta
     * @param y la coordenada y de la planta
     * @param name el nombre de la planta
     * @param cost el costo de la planta
     * @param life la vida de la planta
     * @param type el tipo de la planta
     * @param actionSpeed la velocidad de acción de la planta en milisegundos
     * @param position la posición de la planta en el tablero
     * @param imagePath la ruta a la imagen de la planta
     * @param board el tablero del juego
     */
    public SupportPlant(int x, int y, String name, int cost, int life, String type, int actionSpeed, Point position, String imagePath, Board board) {
        super(x, y, name, cost, life, type, position, imagePath, board);
        this.actionSpeed = actionSpeed;
    }

    /**
     * Inicia la acción de la planta de apoyo.
     * Configura un temporizador para llamar al método de acción a intervalos definidos por la velocidad de acción.
     */
    public void startAction() {
        if (actionSpeed > 0) {
            timer = new Timer(actionSpeed, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    action();
                }
            });
            timer.start();
        }
    }

    /**
     * Método abstracto para realizar la acción de la planta de apoyo.
     * Este método debe ser implementado por las subclases.
     */
    public abstract void action(); // Definido por subclases para realizar su acción específica

    /**
     * Detiene la acción de la planta de apoyo.
     * Detiene el temporizador de acción si está en funcionamiento.
     */
    public void stopAction() {
        if (timer != null) {
            timer.stop();
        }
    }
}