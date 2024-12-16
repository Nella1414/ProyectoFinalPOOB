// ZombieFactory.java
package domain.entities;

import java.awt.Point;
import domain.board.Board;
import presentation.GameEasyWindow;

/**
 * La interfaz ZombieFactory define un método para crear zombies en el juego.
 */
public interface ZombieFactory {

    /**
     * Crea un zombie con las coordenadas, el tablero, la posición y la ventana del juego especificados.
     *
     * @param x la coordenada x del zombie
     * @param y la coordenada y del zombie
     * @param board el tablero del juego
     * @param position la posición del zombie en el tablero
     * @param gameWindow la ventana del juego
     * @return un nuevo objeto Zombie
     */
    Zombie createZombie(int x, int y, Board board, Point position, GameEasyWindow gameWindow);
}