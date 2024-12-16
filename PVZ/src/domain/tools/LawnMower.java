package domain.tools;
import domain.board.Board;
import domain.entities.*;

import java.awt.*;
import java.util.List;

/**
 * La clase LawnMower representa una podadora en el juego.
 * Extiende la clase Tool y define las propiedades y acciones específicas de una LawnMower.
 */
public class LawnMower extends Tool {
    boolean active;
    int row;
    Point position;

    /**
     * Construye una LawnMower con el nombre y la fila especificados.
     *
     * @param name el nombre de la podadora
     * @param row la fila en la que se encuentra la podadora
     */
    public LawnMower(String name, int row) {
        super(name);
        this.active = false;
        this.row = row;
        this.position = new Point(0, row); // Inicia en la posición más a la izquierda
    }

    /**
     * Realiza la acción de la podadora en la posición especificada.
     *
     * @param position la posición en la que se realiza la acción
     */
    @Override
    public void action(Point position) {
        System.out.println("LawnMower clears the row at y = " + position.y);
        // Elimina zombis en la fila específica.
    }

    /**
     * Activa la podadora en el tablero.
     *
     * @param board el tablero del juego
     */
    public void activate(Board board) {
        if (!active) {
            active = true;
            System.out.println("LawnMower activada en la fila " + row);
            move(board);
        }
    }

    /**
     * Mueve la podadora por toda la fila.
     *
     * @param board el tablero del juego
     */
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