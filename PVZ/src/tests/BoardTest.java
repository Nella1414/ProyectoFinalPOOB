package tests;

import domain.board.Board;
import domain.board.BoardDay;
import domain.entities.Plant;
import domain.entities.Sunflower;
import domain.entities.Zombie;
import domain.entities.BasicZombieFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new BoardDay(1000);
    }

    @Test
    void testAddPlant() {
        Plant plant = new Sunflower(0, 0, board, new Point(0, 0));
        boolean added = board.addPlant(plant, new Point(0, 0));
        assertTrue(added);
        assertEquals(plant, board.getPlantAt(new Point(0, 0)));
    }

    @Test
    void testAddPlantInsufficientSuns() {
        board.spendSuns(1000); // Gastar todos los soles
        Plant plant = new Sunflower(0, 0, board, new Point(0, 0));
        boolean added = board.addPlant(plant, new Point(0, 0));
        assertFalse(added);
    }

    @Test
    void testAddPlantOutOfBounds() {
        Plant plant = new Sunflower(0, 0, board, new Point(0, 0));
        boolean added = board.addPlant(plant, new Point(10, 10)); // Fuera de los límites del tablero
        assertFalse(!added);
    }

    @Test
    void testRemovePlant() {
        Plant plant = new Sunflower(0, 0, board, new Point(0, 0));
        board.addPlant(plant, new Point(0, 0));
        board.removePlant(new Point(0, 0));
        assertNull(board.getPlantAt(new Point(0, 0)));
    }

 // BoardTest.java
@Test
void testAddZombie() {
    Zombie zombie = new BasicZombieFactory().createZombie(100, 100, board, new Point(100, 100), null);
    boolean added = board.addZombie(zombie, new Point(0, 0));
    assertTrue(added);
    assertEquals(zombie, board.getZombieAt(new Point(0, 0)));
}

@Test
void testRemoveZombie() {
    Zombie zombie = new BasicZombieFactory().createZombie(100, 100, board, new Point(100, 100), null);
    board.addZombie(zombie, new Point(0, 0));
    board.removeZombie(new Point(0, 0));
    assertNull(board.getZombieAt(new Point(0, 0)));
}

    @Test
    void testSpendSuns() {
        boolean spent = board.spendSuns(500);
        assertTrue(spent);
        assertEquals(500, board.getSunPoints());
    }

    @Test
    void testSpendSunsInsufficient() {
        boolean spent = board.spendSuns(1500);
        assertFalse(spent);
        assertEquals(1000, board.getSunPoints());
    }

    @Test
    void testAddSun() {
        board.addSun(500);
        assertEquals(1500, board.getSunPoints());
    }

    @Test
    void testFindTargetInRow() {
        Zombie zombie = new BasicZombieFactory().createZombie(100, 100, board, new Point(100, 100), null);
        board.addZombie(zombie, new Point(0, 0));
        assertEquals(zombie, board.findTargetInRow(0));
        assertNull(board.findTargetInRow(1));
    }

    @Test
    void testGetZombiesAtPosition() {
        Zombie zombie1 = new BasicZombieFactory().createZombie(100, 100, board, new Point(100, 100), null);
        Zombie zombie2 = new BasicZombieFactory().createZombie(100, 100, board, new Point(100, 100), null);
        board.addZombie(zombie1, new Point(0, 0));
        board.addZombie(zombie2, new Point(0, 0));
        assertEquals(0, board.getZombiesAtPosition(new Point(0, 10)).size());
    }

    @Test
    void testCheckPotatoMine() {
        // Implementar un test para verificar el comportamiento de la mina de papa
    }

    @Test
    void testUpdateZombiesWhenLawnMower() {
        // Implementar un test para verificar el comportamiento de los zombis cuando se activa la podadora
    }
}