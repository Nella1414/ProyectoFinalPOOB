package domain.board;

/**
 * La clase BoardNight extiende Board y maneja eventos especiales nocturnos.
 */
public class BoardNight extends Board {

    /**
     * Construye un BoardNight con un número fijo de filas, columnas y puntos de sol iniciales.
     */
    public BoardNight() {
        super(5, 9, 100);
    }

    /**
     * Genera un evento especial nocturno.
     * Imprime un mensaje indicando la activación de hongos nocturnos.
     * Aquí se puede añadir la lógica para generar hongos o activar efectos de niebla.
     */
    public void generateSpecialEvent() {
        System.out.println("Activando hongos nocturnos...");
        // Lógica para generar hongos o activar efectos de niebla
    }
}