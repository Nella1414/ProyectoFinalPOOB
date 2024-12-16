// BasicZombieFactory.java
package domain.entities;

import java.awt.Point;
import domain.board.Board;
import presentation.GameEasyWindow;

/**
 * La clase BasicZombieFactory implementa la interfaz ZombieFactory.
 * Se encarga de crear instancias de BasicZombie.
 */
public class BasicZombieFactory implements ZombieFactory {
    /**
     * Crea un nuevo zombi básico con las coordenadas, el tablero, la posición y la ventana del juego especificados.
     *
     * @param x la coordenada x del zombi
     * @param y la coordenada y del zombi
     * @param board el tablero del juego
     * @param position la posición del zombi en el tablero
     * @param gameWindow la ventana del juego
     * @return una nueva instancia de BasicZombie
     */
    @Override
    public Zombie createZombie(int x, int y, Board board, Point position, GameEasyWindow gameWindow) {
        return new BasicZombie(x, y, board, position, gameWindow);
    }
}