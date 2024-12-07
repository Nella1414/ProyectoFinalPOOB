// ZombieFactory.java
package domain.entities;

import java.awt.Point;
import domain.board.Board;
import presentation.GameEasyWindow;

public interface ZombieFactory {
    Zombie createZombie(Board board, Point position, GameEasyWindow gameWindow);
}