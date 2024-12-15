package tests;

import domain.board.Board;
import domain.board.BoardDay;
import domain.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presentation.GameEasyWindow;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ZombieTest {
    private Board board;
    private GameEasyWindow gameWindow;
    private List<Entity> entitiesForHud;

    @BeforeEach
    void setUp() {
        board = new BoardDay(1000);
        entitiesForHud = List.of(new Sunflower(0, 0, board, new Point(0, 0)), new Peashooter(0, 0, board, new Point(0, 0)));
        gameWindow = new GameEasyWindow("Novato", new ArrayList<>(entitiesForHud), board);
    }

    @Test
    void testZombieCreation() {
        Zombie zombie = new BasicZombieFactory().createZombie(100, 100, board, new Point(100, 100), gameWindow);
        assertNotNull(zombie);
        assertEquals(100, zombie.getLife());
        assertEquals(100, zombie.getDamage());
    }

    @Test
    void testZombieMovement() {
        Zombie zombie = new BasicZombieFactory().createZombie(100, 100, board, new Point(100, 100), gameWindow);
        board.addZombie(zombie, new Point(0, 0));
        zombie.move(board);
        assertEquals(98, zombie.getX());
    }

    @Test
    void testZombieAttack() {
        Zombie zombie = new BasicZombieFactory().createZombie(100, 100, board, new Point(100, 100), gameWindow);
        Plant plant = new Sunflower(0, 0, board, new Point(0, 0));
        board.addPlant(plant, new Point(0, 0));
        zombie.startAttacking(board, plant);
        assertTrue(zombie.isAlive());
        assertTrue(plant.getLife() > 100);
    }

    @Test
    void testZombieReceiveDamage() {
        Zombie zombie = new BasicZombieFactory().createZombie(100, 100, board, new Point(100, 100), gameWindow);
        zombie.receiveDamage(50);
        assertEquals(50, zombie.getLife());
        zombie.receiveDamage(60);
        assertFalse(zombie.isAlive());
    }

    @Test
    void testZombieFreezeAndUnfreeze() {
        Zombie zombie = new BasicZombieFactory().createZombie(100, 100, board, new Point(100, 100), gameWindow);
        zombie.freeze();
        assertTrue(zombie.getIsfreezed());
        zombie.unfreeze();
        assertFalse(zombie.getIsfreezed());
    }
}