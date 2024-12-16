package domain.board;

import domain.entities.*;
import domain.tools.LawnMower;
import domain.tools.Tool;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Clase abstracta que representa el tablero del juego.
 * Gestiona plantas, zombis, puntos de sol y cortadoras de césped.
 */
public abstract class Board {
    private final Map<Integer, LawnMower> lawnMowers;
    protected int rows;
    protected int columns;
    protected Map<Point, Plant> plants;
    protected Map<Point, Zombie> zombies;
    private int sunPoints;

    private ArrayList<Zombie> zombiesList = new ArrayList<>();
    private ArrayList<Plant> plantsList = new ArrayList<>();
    private ArrayList<shoot> shoots = new ArrayList<>();

    /**
     * Construye un tablero con el número especificado de filas, columnas y puntos de sol iniciales.
     *
     * @param rows el número de filas en el tablero
     * @param columns el número de columnas en el tablero
     * @param sunPoints el número inicial de puntos de sol
     */
    public Board(int rows, int columns, int sunPoints) {
        this.rows = rows;
        this.columns = columns;
        this.plants = new HashMap<>();
        this.zombies = new HashMap<>();
        this.sunPoints = sunPoints;
        this.lawnMowers = new HashMap<>();
        for (int i = 0; i < rows; i++) {
            lawnMowers.put(i, new LawnMower("LawnMower", i));
        }
        updateZombiesWhenLawnMower();
    }

    /**
     * Añade un disparo al tablero.
     *
     * @param shoot el disparo a añadir
     */
    public void addShoot(shoot shoot) {
        shoots.add(shoot);
    }

    /**
     * Devuelve la lista de disparos en el tablero.
     *
     * @return la lista de disparos
     */
    public ArrayList<shoot> getShoots() {
        return shoots;
    }

    /**
     * Elimina un disparo del tablero.
     *
     * @param shoot el disparo a eliminar
     */
    public void removeShoot(shoot shoot) {
        shoots.remove(shoot);
    }

    /**
     * Convierte una coordenada y en un índice de fila para las plantas.
     *
     * @param y la coordenada y
     * @return el índice de fila
     */
    public int getRowFromYForPlants(int y) {
        return ((y - 270) / 90) + 1;
    }

    /**
     * Convierte una coordenada y en un índice de fila para los zombis.
     *
     * @param y la coordenada y
     * @return el índice de fila
     */
    public int getRowFromYForZombies(int y) {
        return ((y - 90) / 90);
    }

    /**
     * Convierte una coordenada x en un índice de columna.
     *
     * @param x la coordenada x
     * @return el índice de columna
     */
    public int getColumnFromX(int x) {
        return (x - 100) / 35;
    }

    /**
     * Comprueba si hay una planta en la columna especificada.
     *
     * @param column el índice de columna
     * @return true si hay una planta en la columna, false en caso contrario
     */
    public boolean isPlantInColumn(int column) {
        for (Plant plant : plants.values()) {
            int plantColumn = getColumnFromX(plant.getPosition().x);
            if (plantColumn == column) {
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve el número actual de puntos de sol.
     *
     * @return el número de puntos de sol
     */
    public int getSunPoints() {
        return sunPoints;
    }

    /**
     * Añade puntos de sol al tablero.
     *
     * @param sunPoints el número de puntos de sol a añadir
     */
    public void addSun(int sunPoints) {
        this.sunPoints += sunPoints;
        System.out.println("Soles actuales: " + this.sunPoints);
    }

    /**
     * Gasta la cantidad especificada de puntos de sol.
     *
     * @param amount la cantidad de puntos de sol a gastar
     * @return true si los puntos de sol se gastaron con éxito, false en caso contrario
     */
    public boolean spendSuns(int amount) {
        if (this.sunPoints >= amount) {
            this.sunPoints -= amount;
            System.out.println("Soles restantes: " + this.sunPoints);
            return true;
        }
        System.out.println("No hay suficientes soles para gastar.");
        return false;
    }

    /**
     * Añade una planta al tablero en la posición especificada.
     *
     * @param plant la planta a añadir
     * @param position la posición para añadir la planta
     * @return true si la planta se añadió con éxito, false en caso contrario
     */
    public boolean addPlant(Plant plant, Point position) {
        plantsList.add(plant);
        if (position.x >= columns || position.y >= rows) {
            System.out.println("La posición " + position + " está fuera de los límites del tablero.");
            return false;
        }
        if (plants.containsKey(position)) {
            System.out.println("La posición " + position + " ya está ocupada por otra planta.");
            return false;
        }
        if (plant.getCost() > sunPoints) {
            System.out.println("No hay suficientes soles para la planta: " + plant.getName());
            return false;
        }
        sunPoints -= plant.getCost();
        plants.put(position, plant);
        System.out.println("Se añadió la planta " + plant.getName() + " en posición " + position + ". Soles restantes: " + sunPoints);
        return true;
    }

    /**
     * Añade un zombi al tablero en la posición especificada.
     *
     * @param zombie el zombi a añadir
     * @param position la posición para añadir el zombi
     * @return true si el zombi se añadió con éxito, false en caso contrario
     */
    public boolean addZombie(Zombie zombie, Point position) {
        zombiesList.add(zombie);
        if (position.y >= rows || zombies.containsKey(position)) {
            return false;
        }
        zombies.put(position, zombie);
        return true;
    }

    /**
     * Devuelve la planta en la posición especificada.
     *
     * @param position la posición a comprobar
     * @return la planta en la posición, o null si no hay planta
     */
    public Plant getPlantAt(Point position) {
        return plants.get(position);
    }

    /**
     * Devuelve el zombi en la posición especificada.
     *
     * @param position la posición a comprobar
     * @return el zombi en la posición, o null si no hay zombi
     */
    public Zombie getZombieAt(Point position) {
        return zombies.get(position);
    }

    /**
     * Devuelve el número de filas en el tablero.
     *
     * @return el número de filas
     */
    public int getRows() {
        return rows;
    }

    /**
     * Devuelve el número de columnas en el tablero.
     *
     * @return el número de columnas
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Devuelve un mapa de todas las plantas en el tablero.
     *
     * @return un mapa de plantas
     */
    public Map<Point, Plant> getPlants() {
        return plants;
    }

    /**
     * Devuelve un mapa de todos los zombis en el tablero.
     *
     * @return un mapa de zombis
     */
    public Map<Point, Zombie> getZombies() {
        return zombies;
    }

    /**
     * Encuentra el primer zombi en la fila especificada.
     *
     * @param row la fila a comprobar
     * @return el primer zombi en la fila, o null si no hay zombi
     */
    public Zombie findTargetInRow(int row) {
        for (Point pos : zombies.keySet()) {
            if (pos.y == row) return zombies.get(pos);
        }
        return null;
    }

    /**
     * Elimina la planta en la posición especificada.
     *
     * @param position la posición para eliminar la planta
     */
    public void removePlant(Point position) {
        plants.remove(position);
    }

    /**
     * Elimina la planta especificada de la lista de plantas.
     *
     * @param plant la planta a eliminar
     */
    public void removePlantList(Plant plant) {
        if (plant instanceof Peashooter) {
            Peashooter peaShooter = (Peashooter) plant;
            peaShooter.stopAttacking();
            peaShooter.stopShooting();
        }
        plantsList.remove(plant);
    }

    /**
     * Elimina el zombi especificado de la lista de zombis.
     *
     * @param zombie el zombi a eliminar
     */
    public void removeZombieList(Zombie zombie) {
        zombiesList.remove(zombie);
    }

    /**
     * Elimina el zombi en la posición especificada.
     *
     * @param position la posición para eliminar el zombi
     */
    public void removeZombie(Point position) {
        zombies.remove(position);
    }

    /**
     * Usa una herramienta en la posición especificada.
     *
     * @param tool la herramienta a usar
     * @param position la posición para usar la herramienta
     */
    public void useShovel(Tool tool, Point position) {
        tool.action(position);
        if (tool.getName().equals("Shovel") && plants.containsKey(position)) {
            this.removePlant(position);
            System.out.println("Planta eliminada del tablero");
        } else {
            System.out.println("No se encontró planta en " + position + ". Nada que eliminar.");
        }
    }

    /**
     * Comprueba si hay una mina de papa en la posición especificada y la detona si la hay.
     *
     * @param position la posición a comprobar
     */
    public void checkPotatoMine(Point position) {
        Plant plant = plants.get(position);
        if (plant instanceof PotatoMine) {
            Zombie target = zombies.get(position);
            ((PotatoMine) plant).detonate(target, this);
            if (plant.getLife() <= 0) {
                plants.remove(position); // Eliminar la mina tras explotar.
            }
        }
    }

    /**
     * Encuentra el zombi más cercano en la fila especificada comenzando desde la columna especificada.
     *
     * @param row la fila a comprobar
     * @param startColumn la columna para comenzar a comprobar
     * @return el zombi más cercano en la fila, o null si no hay zombi
     */
    public Zombie findClosestZombieInRow(int row, int startColumn) {
        Zombie closestZombie = null;
        int minDistance = Integer.MAX_VALUE;
        for (Point pos : zombies.keySet()) {
            int zombieRow = getRowFromYForZombies(pos.y);
            if (zombieRow == row && pos.x >= startColumn) {
                int distance = pos.x - startColumn;
                if (distance < minDistance) {
                    minDistance = distance;
                    closestZombie = zombies.get(pos);
                }
            }
        }
        return closestZombie;
    }

    /**
     * Imprime el estado actual del tablero.
     */
    public void printBoardState() {
        for (Point pos : plants.keySet()) {
            Plant plant = plants.get(pos);
        }
        for (Point pos : zombies.keySet()) {
            Zombie zombie = zombies.get(pos);
            int zombieRow = getRowFromYForZombies(pos.y);
        }
    }

    /**
     * Devuelve una lista de zombis en la posición especificada.
     *
     * @param position la posición a comprobar
     * @return una lista de zombis en la posición
     */
    public List<Zombie> getZombiesAtPosition(Point position) {
        return zombies.values().stream().filter(zombie -> zombie.getPosition().equals(position)).collect(Collectors.toList());
    }

    /**
     * Actualiza los zombis cuando se activa una cortadora de césped.
     */
    public void updateZombiesWhenLawnMower() {
        List<Map.Entry<Point, Zombie>> entries = new ArrayList<>(zombies.entrySet());
        List<Point> toRemove = new ArrayList<>();
        for (Point position : toRemove) {
            zombies.remove(position);
        }
    }

    /**
     * Devuelve la lista de todos los zombis en el tablero.
     *
     * @return la lista de zombis
     */
    public ArrayList<Zombie> getZombiesList() {
        return zombiesList;
    }

    /**
     * Devuelve la lista de todas las plantas en el tablero.
     *
     * @return la lista de plantas
     */
    public ArrayList<Plant> getPlantsList() {
        return plantsList;
    }
}