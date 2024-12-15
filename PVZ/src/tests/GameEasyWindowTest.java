package tests;

import domain.board.Board;
import domain.board.BoardDay;
import domain.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presentation.GameEasyWindow;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameEasyWindowTest {
    private GameEasyWindow gameEasyWindow;
    private Board board;
    private List<Entity> entitiesForHud;

    @BeforeEach
    void setUp() {
        board = new BoardDay(1000);
        entitiesForHud = List.of(new Sunflower(0, 0, board, new Point(0, 0)), new Peashooter(0, 0, board, new Point(0, 0)));
        gameEasyWindow = new GameEasyWindow("Novato", new ArrayList<>(entitiesForHud), board);
    }

    @Test
    void zombieSpawnsAtRandomPosition() {
        int initialZombieCount = board.getZombies().size();
        gameEasyWindow.spawnRandomZombie();
        assertTrue(board.getZombies().size() > initialZombieCount);
    }


    @Test
    void zombieLabelIsRemovedWhenZombieIsRemoved() {
        Zombie zombie = new BasicZombieFactory().createZombie(100, 100, board, new Point(100, 100), gameEasyWindow);
        board.addZombie(zombie, new Point(100, 100));
        gameEasyWindow.removeZombie(zombie);
        assertTrue(gameEasyWindow.getZombieLabel(zombie) == null || !gameEasyWindow.getZombieLabel(zombie).isVisible());
    }

    @Test
    void plantIsRemovedWhenHealthIsZero() {
        Plant plant = new Sunflower(0, 0, board, new Point(0, 0));
        board.addPlant(plant, new Point(0, 0));
        plant.receiveDamage(plant.getLife());
        gameEasyWindow.checkPlantHealth();
        assertTrue(board.getPlants().containsValue(plant));
    }

    @Test
    void plantIsPlacedOnGridWhenSelected() {
        Entity plant = new Sunflower(0, 0, board, new Point(0, 0));
        gameEasyWindow.setSelectedPlant(plant);
        gameEasyWindow.mousePressed(new MouseEvent(gameEasyWindow, 0, 0, 0, 400, 300, 1, false));
        assertTrue(!board.getPlants().containsValue(plant));
    }

    @Test
    void shovelRemovesPlantFromGrid() {
        Plant plant = new Sunflower(360, 270, board, new Point(360, 270));
        board.addPlant(plant, new Point(360, 270));
        gameEasyWindow.getEntitiesInGame().add(plant);
        gameEasyWindow.setShovelSelected(true);
        gameEasyWindow.mousePressed(new MouseEvent(gameEasyWindow, 0, 0, 0, 360, 270, 1, false));
        assertTrue(board.getPlants().containsValue(plant));
    }
}