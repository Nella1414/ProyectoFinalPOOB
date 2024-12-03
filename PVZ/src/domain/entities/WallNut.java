package domain.entities;

import domain.board.Board;

import java.awt.*;

public class WallNut extends DefensivePlant {
    public WallNut(Board board,Point position) {
        super("WallNut", 50, 4000, "DefensivePlant",position,"assets/Images/inGame/plants/WallNut/WallNut.gif",board);}
}
