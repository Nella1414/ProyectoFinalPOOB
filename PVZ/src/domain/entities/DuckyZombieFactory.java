
package domain.entities;

import java.awt.Point;
import domain.board.Board;
import presentation.GameEasyWindow;

public class DuckyZombieFactory  implements  ZombieFactory{
    @Override
    public Zombie createZombie(int x, int y, Board board, Point position, GameEasyWindow gameWindow) {
        return new DuckyZombie(x,y, board, position, gameWindow);
    }
}
