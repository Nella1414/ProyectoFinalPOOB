package domain.entities;

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import domain.board.Board;

/**
 * La clase Sunflower representa una planta girasol en el juego.
 * Extiende la clase SupportPlant y define las propiedades y acciones específicas de un Sunflower.
 */
public class Sunflower extends SupportPlant {

    /**
     * Construye un Sunflower con las coordenadas, el tablero y la posición especificados.
     *
     * @param x la coordenada x de la planta
     * @param y la coordenada y de la planta
     * @param board el tablero del juego
     * @param position la posición de la planta en el tablero
     */
    public Sunflower(int x, int y, Board board, Point position) {
        super(x, y, "Sunflower", 50, 300, "SupportPlant", 20000, position, "assets/Images/inGame/plants/SunFlower.gif", board); // 20 segundos = 20000 ms
        this.startAction();
    }

    /**
     * Realiza la acción del Sunflower.
     * Genera 25 soles y los añade al tablero.
     */
    @Override
    public void action() {
        if (board != null) {
            board.addSun(25);
            System.out.println(this.getName() + " ha generado 25 soles.");
        }
    }

    /**
     * Realiza la acción de ataque del Sunflower.
     * Actualmente, este método no implementa ninguna lógica de ataque.
     *
     * @param board el tablero del juego
     */
    @Override
    public void attack(Board board) {

    }
}