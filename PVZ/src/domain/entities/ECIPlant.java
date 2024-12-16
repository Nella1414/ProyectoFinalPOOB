package domain.entities;

import domain.board.Board;

import java.awt.*;

/**
 * La clase ECIPlant representa una planta de soporte en el juego.
 * Extiende la clase SupportPlant y define las propiedades y acciones específicas de una ECIPlant.
 */
public class ECIPlant extends SupportPlant {

    /**
     * Construye una ECIPlant con las coordenadas, el tablero y la posición especificados.
     *
     * @param x la coordenada x de la planta
     * @param y la coordenada y de la planta
     * @param board el tablero del juego
     * @param position la posición de la planta en el tablero
     */
    public ECIPlant(int x, int y, Board board, Point position) {
        super(x, y, "ECIPlant", 75, 150, "SupportPlant", 20000, position, "assets/Images/inGame/plants/ECIPlant.gif", board);
    }

    /**
     * Realiza la acción de la ECIPlant.
     * Si el tablero no es nulo, añade 50 puntos de sol al tablero e imprime un mensaje indicando que la planta generó sol.
     */
    @Override
    public void action() {
        if (board != null) {
            board.addSun(50);
            System.out.println(this.getName() + " generó un sol.");
            // Aquí puedes añadir lógica para incrementar recursos o actualizar el tablero
        }
    }

    /**
     * Realiza la acción de ataque de la ECIPlant.
     * Actualmente, este método no implementa ninguna lógica de ataque.
     *
     * @param board el tablero del juego
     */
    @Override
    public void attack(Board board) {
        // No se implementa lógica de ataque
    }
}