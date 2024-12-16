package domain.entities;

import domain.board.Board;
import presentation.GameEasyWindow;

import java.awt.*;

/**
 * La clase DuckyZombie representa un zombi con flotador en el juego.
 * Extiende la clase Zombie y define las propiedades específicas de un zombi con flotador.
 */
public class DuckyZombie extends Zombie {
    Board board;

    /**
     * Construye un DuckyZombie con las coordenadas, el tablero, la posición y la ventana del juego especificados.
     *
     * @param x la coordenada x del zombi
     * @param y la coordenada y del zombi
     * @param board el tablero del juego
     * @param position la posición del zombi en el tablero
     * @param gameWindow la ventana del juego
     */
    public DuckyZombie(int x, int y, Board board, Point position, GameEasyWindow gameWindow) {
        super(x, y, "DuckyZombie", 25, 100, 3, 100, 0.5f, "Básico", board, position, "assets/Images/inGame/zombies/DuckyZombie.gif", gameWindow);
    }
}