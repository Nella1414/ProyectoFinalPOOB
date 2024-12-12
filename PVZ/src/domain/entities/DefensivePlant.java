package domain.entities;

import domain.board.Board;

import java.awt.*;

public class DefensivePlant extends Plant {
    public DefensivePlant(int x, int y,String name, int cost, int life, String type, Point position, String imagePath, Board board) {
        super(x,y,name, cost, life, type, position, imagePath, board);
    }

    @Override
    public void attack(Board board) {
        System.out.println(this.getName() + " atacó.");
    }
}
