package domain.entities;

import domain.board.Board;

import java.awt.*;

public class DefensivePlant extends Plant {
    public DefensivePlant(String name, int cost, int life, String type, Point position, String imagePath, Board board) {
        super(name, cost, life, type, position, imagePath, board);
    }
}
