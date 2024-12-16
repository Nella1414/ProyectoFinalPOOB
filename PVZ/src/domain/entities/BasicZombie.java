package domain.entities;

import domain.board.Board;
import presentation.GameEasyWindow;

import java.awt.*;

/**
 * La clase BasicZombie representa un zombi básico en el juego.
 * Extiende la clase Zombie y define las propiedades específicas de un zombi básico.
 */
public class BasicZombie extends Zombie {
    Board board;

    /**
     * Construye un BasicZombie con las coordenadas, el tablero, la posición y la ventana del juego especificados.
     *
     * @param x la coordenada x del zombi
     * @param y la coordenada y del zombi
     * @param board el tablero del juego
     * @param position la posición del zombi en el tablero
     * @param gameWindow la ventana del juego
     */
    public BasicZombie(int x, int y, Board board, Point position, GameEasyWindow gameWindow) {
        super(x, y, "BasicZombie", 25, 100, 10, 100, 0.5f, "Básico", board, position, "assets/Images/inGame/zombies/BasicZombie.gif", gameWindow);
    }
}