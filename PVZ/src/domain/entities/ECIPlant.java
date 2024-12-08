package domain.entities;

import domain.board.Board;

import java.awt.*;

public class ECIPlant extends SupportPlant{

    public ECIPlant(Board board, Point position) {
        super("ECIPlant",75,150,"SupportPlant",20000,position, "assets/Images/inGame/plants/ECIPlant.gif",board);
    }
    @Override
    public void action() {
        if (board != null) {
            board.addSun(50);
        System.out.println(this.getName() + " generó un sol.");
        // Aquí puedes agregar lógica para aumentar recursos o actualizar el tablero
        }
    }

    @Override
    public void attack(Board board) {

    }
}
