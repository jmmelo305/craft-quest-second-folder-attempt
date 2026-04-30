/*
Programmer: Jose Melo
Program: WorldTest.java
Purpose: JUnit Tests for World generation system.
Date: April 28, 2026
*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WorldTest {

    private World world;

    @BeforeEach
    public void setup() {
        world = new World(10, 10, new FixedMapStrategy());
    }

    @Test
    public void testCorrectWidth() {
        assertEquals(10, world.getWidth());
    }

    @Test
    public void testCorrectHeight() {
        assertEquals(10, world.getHeight());
    }

    @Test
    public void testColumnFourIsWater() {
        // FixedMapStrategy places water along entire column 4
        assertEquals(Tile.Type.WATER, world.getTile(4, 0).getType());
        assertEquals(Tile.Type.WATER, world.getTile(4, 5).getType());
    }

    @Test
    public void testSpawnTileIsWalkable() {
        assertTrue(world.getTile(0, 0).isWalkable());
    }

    @Test
    public void testNotAllChestsCollectedAtStart() {
        assertFalse(world.allChestsCollected());
    }

    @Test
    public void testAllChestsCollectedAfterManualCollection() {
        // Manually collect every chest on the map
        for (int row = 0; row < world.getHeight(); row++)
            for (int col = 0; col < world.getWidth(); col++) {
                Tile t = world.getTile(col, row);
                if (t.getType() == Tile.Type.CHEST) t.collect();
            }
        assertTrue(world.allChestsCollected());
    }
}
