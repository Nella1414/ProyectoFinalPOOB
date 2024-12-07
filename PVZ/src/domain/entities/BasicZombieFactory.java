// BasicZombieFactory.java
package domain.entities;

import java.awt.Point;
import domain.board.Board;
import presentation.GameEasyWindow;

public class BasicZombieFactory implements ZombieFactory {
    @Override
    public Zombie createZombie(Board board, Point position, GameEasyWindow gameWindow) {
        return new BasicZombie(board, position, gameWindow);
    }
}