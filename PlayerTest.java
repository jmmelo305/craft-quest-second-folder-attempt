/*
Programmer: Jose Melo
Program: PlayerTest.java
Purpose: JUnit Tests for Player movement and location
Date: April 28, 2026
*/


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private World world;
    private Player player;

    @BeforeEach
    public void setup() {
        // FixedMapStrategy: column 4 is all WATER, spawn at (0,0) is GRASS
        world  = new World(10, 10, new FixedMapStrategy());
        player = new Player(0, 0);
    }

    @Test
    public void testMoveRight() {
        player.move(1, 0, world);
        assertEquals(1, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    public void testMoveDown() {
        player.move(0, 1, world);
        assertEquals(0, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    public void testCannotMoveOutOfBoundsLeft() {
        player.move(-1, 0, world); // already at x=0
        assertEquals(0, player.getX());
    }

    @Test
    public void testCannotMoveOutOfBoundsTop() {
        player.move(0, -1, world); // already at y=0
        assertEquals(0, player.getY());
    }

    @Test
    public void testCannotMoveIntoWater() {
        // Column 4 is water in FixedMapStrategy
        // Place player at x=3 and try to walk right into water
        Player p = new Player(3, 0);
        p.move(1, 0, world);
        assertEquals(3, p.getX()); // blocked — should not move
    }

    @Test
    public void testCollectingChestAddsToInventory() {
        // In FixedMapStrategy, chest is at (8,1) — move player there
        Player p = new Player(7, 1);
        p.move(1, 0, world); // move to (8,1) which has a CHEST
        assertEquals(1, p.getInventory().getItemCount());
    }
}
