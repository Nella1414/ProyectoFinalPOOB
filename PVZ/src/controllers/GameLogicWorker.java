package controllers;

import javax.swing.*;
import java.util.List;
import domain.board.Board;
import presentation.GameEasyWindow;

/**
 * La clase GameLogicWorker extiende SwingWorker para manejar la lógica del juego en un hilo de fondo.
 * Gestiona la aparición de zombis según el nivel de dificultad del juego y actualiza el estado del juego.
 */
public class GameLogicWorker extends SwingWorker<Void, Void> {
    private final Board board;
    private final GameEasyWindow gameWindow;

    /**
     * Construye un GameLogicWorker con el tablero y la ventana del juego especificados.
     * Inicializa los temporizadores de aparición de zombis según el nivel de dificultad.
     *
     * @param board el tablero del juego
     * @param gameWindow la ventana del juego
     */
    public GameLogicWorker(Board board, GameEasyWindow gameWindow) {
        gameWindow.spawnRandomZombie();
        if (gameWindow.getDifficulty() == "Medio") {
            Timer zombieSpawnTimer = new Timer(5000, e -> gameWindow.spawnRandomZombie()); // Cada 5 segundos
            zombieSpawnTimer.start();
        }
        if (gameWindow.getDifficulty() == "Experto") {
            Timer zombieSpawnTimer = new Timer(3000, e -> gameWindow.spawnRandomZombie()); // Cada 3 segundos
            zombieSpawnTimer.start();
        }
        else {
            Timer zombieSpawnTimer = new Timer(10000, e -> gameWindow.spawnRandomZombie()); // Cada 10 segundos
            zombieSpawnTimer.start();
        }
        // Usa un temporizador para generar zombis en intervalos regulares

        board.updateZombiesWhenLawnMower();
        this.board = board;
        this.gameWindow = gameWindow;
    }

    /**
     * La tarea principal ejecutada en el hilo de fondo.
     * Simula la lógica del juego durmiendo durante 1 segundo y luego actualizando el estado del tablero.
     *
     * @return null
     * @throws Exception si ocurre un error durante la ejecución
     */
    @Override
    protected Void doInBackground() throws Exception {
        while (true) {
            Thread.sleep(1000); // Simular la lógica del juego corriendo
            board.printBoardState(); // Ejecutar la lógica del juego
            publish(); // Actualizar la GUI
        }
    }

    /**
     * Procesa los resultados intermedios publicados por el método doInBackground.
     * Actualiza la etiqueta de soles de la ventana del juego, las posiciones de los zombis y repinta la ventana.
     *
     * @param chunks los resultados intermedios
     */
    @Override
    protected void process(List<Void> chunks) {
        gameWindow.updateSunsLabel(); // Actualizar la etiqueta de soles
        gameWindow.updateAllZombiePositions(); // Actualizar el estado del tablero
        gameWindow.repaint(); // Re-pintar la ventana del juego
    }

    /**
     * Llamado cuando la tarea en segundo plano ha terminado.
     * Imprime un mensaje indicando que la lógica del juego ha terminado.
     */
    @Override
    protected void done() {
        System.out.println("Lógica del juego terminada.");
    }
}