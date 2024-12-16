package domain.entities;

import domain.board.Board;

import java.awt.*;

/**
 * La clase WallNut representa una nuez defensiva en el juego.
 * Extiende la clase DefensivePlant y define las propiedades y acciones específicas de una WallNut.
 */
public class WallNut extends DefensivePlant {

    /**
     * Construye una WallNut con las coordenadas, el tablero y la posición especificados.
     *
     * @param x la coordenada x de la planta
     * @param y la coordenada y de la planta
     * @param board el tablero del juego
     * @param position la posición de la planta en el tablero
     */
    public WallNut(int x, int y, Board board, Point position) {
        super(x, y, "WallNut", 50, 4000, "DefensivePlant", position, "assets/Images/inGame/plants/WallNut/WallNut.gif", board);
    }
}