package domain.entities;

import domain.board.Board;
import presentation.GameEasyWindow;

import java.awt.*;

public class DuckyZombie extends Zombie {
    Board board;

    public DuckyZombie(int x,int y,Board board, Point position, GameEasyWindow gameWindow) {
        super(x,y,"DuckyZombie", 25, 100, 1, 100, 0.5f, "Básico", board, position, "assets/Images/inGame/zombies/DuckyZombie.gif", gameWindow);
    }
}
