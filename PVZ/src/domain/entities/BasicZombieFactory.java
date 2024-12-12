// BasicZombieFactory.java
package domain.entities;

import java.awt.Point;
import domain.board.Board;
import presentation.GameEasyWindow;

public class BasicZombieFactory implements ZombieFactory {
    @Override
    public Zombie createZombie(int x, int y, Board board, Point position, GameEasyWindow gameWindow) {
        return new BasicZombie(x,y, board, position, gameWindow);
    }
}