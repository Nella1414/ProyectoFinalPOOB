package domain.tools;
import domain.board.Board;
import domain.entities.*;

import java.awt.*;
import java.util.List;

public class LawnMower extends Tool {
    boolean active;
    int row;
    Point position;
    public LawnMower(String name,int row) {
        super(name);
        this.active = false;
        this.row = row;
        this.position = new Point(0, row); // Inicia en la posición más a la izquierda

    }

    @Override
    public void action(Point position) {
        System.out.println("LawnMower clears the row at y = " + position.y);
        // Elimina zombis en la fila específica.
    }

    public void activate(Board board) {
        if (!active) {
            active = true;
            System.out.println("LawnMower activada en la fila " + row);
            move(board);
        }
    }

    // Mover la podadora por toda la fila
    public void move(Board board) {
        new Thread(() -> {
            while (active && position.x < board.getColumns()) {
                // Eliminar zombis en la posición actual
                List<Zombie> zombiesInPosition = board.getZombiesAtPosition(position);
                for (Zombie z : zombiesInPosition) {
                    board.removeZombie(z.getPosition());
                    System.out.println("Zombie eliminado por LawnMower en posición " + position);
                }

                // Mover a la siguiente posición
                position.x++;
                try {
                    Thread.sleep(500); // Simular tiempo entre movimientos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Finaliza el movimiento
            active = false;
            System.out.println("LawnMower terminó su recorrido en la fila " + row);
        }).start();
    }


}
