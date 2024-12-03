package domain.entities;

import domain.board.Board;

import java.awt.*;

public class BasicZombie extends Zombie {
    Board board;
    public BasicZombie(Board board, Point position) {
        super("BasicZombie", 25, 100, 1, 100, 0.5f, "Básico", board,position,"assets/Images/inGame/zombies/ConeheadZombie.gif");
    }

}
