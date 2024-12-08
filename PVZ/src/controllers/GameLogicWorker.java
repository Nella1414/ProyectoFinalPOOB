package controllers;

import javax.swing.*;
import java.util.List;
import domain.board.Board;
import presentation.GameEasyWindow;


public class GameLogicWorker extends SwingWorker<Void, Void> {
    private final Board board;
    private final GameEasyWindow gameWindow;

    public GameLogicWorker(Board board, GameEasyWindow gameWindow) {
        gameWindow.spawnRandomZombie();
        if (gameWindow.getDifficulty() == "Medio") {
            Timer zombieSpawnTimer = new Timer(5000, e -> gameWindow.spawnRandomZombie()); // Cada 10 segundos
            zombieSpawnTimer.start();
        }
        if (gameWindow.getDifficulty() == "Experto") {
            Timer zombieSpawnTimer = new Timer(3000, e -> gameWindow.spawnRandomZombie()); // Cada 5 segundos
            zombieSpawnTimer.start();
        }
        else {
            Timer zombieSpawnTimer = new Timer(10000, e -> gameWindow.spawnRandomZombie()); // Cada 20 segundos
            zombieSpawnTimer.start();
        }
        // O usa un temporizador para generar zombis en intervalos regulares

        board.updateZombiesWhenLawnMower();
        this.board = board;
        this.gameWindow = gameWindow;
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (true) {
            Thread.sleep(1000); // Simular la lógica del juego corriendo
            board.printBoardState();// Ejecutar la lógica del juego
            publish(); // Actualizar la GUI
        }
    }

    @Override
    protected void process(List<Void> chunks) {
        gameWindow.updateSunsLabel(); // Actualizar la etiqueta de soles
        gameWindow.updateAllZombiePositions(); // Actualizar el estado del tablero
        gameWindow.repaint(); // Re-pintar la ventana del juego
    }

    @Override
    protected void done() {
        System.out.println("Lógica del juego terminada.");
    }
}