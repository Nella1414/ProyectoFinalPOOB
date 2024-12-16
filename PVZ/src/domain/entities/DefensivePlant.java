package domain.entities;

import domain.board.Board;

import java.awt.*;

/**
 * La clase DefensivePlant representa una planta defensiva en el juego.
 * Extiende la clase Plant y define las propiedades específicas de una planta defensiva.
 */
public class DefensivePlant extends Plant {

    /**
     * Construye una DefensivePlant con las coordenadas, el nombre, el costo, la vida, el tipo, la posición, la ruta de la imagen y el tablero especificados.
     *
     * @param x la coordenada x de la planta
     * @param y la coordenada y de la planta
     * @param name el nombre de la planta
     * @param cost el costo de la planta en puntos de sol
     * @param life la vida de la planta
     * @param type el tipo de planta
     * @param position la posición de la planta en el tablero
     * @param imagePath la ruta de la imagen de la planta
     * @param board el tablero del juego
     */
    public DefensivePlant(int x, int y, String name, int cost, int life, String type, Point position, String imagePath, Board board) {
        super(x, y, name, cost, life, type, position, imagePath, board);
    }

    /**
     * Realiza un ataque en el tablero.
     * Imprime un mensaje indicando que la planta ha atacado.
     *
     * @param board el tablero del juego
     */
    @Override
    public void attack(Board board) {
        System.out.println(this.getName() + " atacó.");
    }
}