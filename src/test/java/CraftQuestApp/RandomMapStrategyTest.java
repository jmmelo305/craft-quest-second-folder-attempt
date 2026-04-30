package CraftQuestApp;

/*
Programmer: Jose Melo
Program: RandomMapStrategyTest.java
Purpose: JUnit Tests for RandomMapStrategy
Date: April 29, 2026
*/

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RandomMapStrategyTest {

    @Test
    public void testGeneratesCorrectDimensions() {
        RandomMapStrategy strategy = new RandomMapStrategy();
        Tile[][] grid = strategy.generate(10, 8);
        assertEquals(8, grid.length);    // height
        assertEquals(10, grid[0].length); // width
    }

    @Test
    public void testSpawnPointIsGrass() {
        RandomMapStrategy strategy = new RandomMapStrategy();
        Tile[][] grid = strategy.generate(10, 10);
        assertEquals(Tile.Type.GRASS, grid[0][0].getType());
    }

    @Test
    public void testContainsExactlyThreeChests() {
        RandomMapStrategy strategy = new RandomMapStrategy();
        Tile[][] grid = strategy.generate(20, 20);
        
        int chestCount = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col].getType() == Tile.Type.CHEST) {
                    chestCount++;
                }
            }
        }
        assertEquals(3, chestCount);
    }

    @Test
    public void testGeneratesDifferentMaps() {
        RandomMapStrategy strategy1 = new RandomMapStrategy();
        RandomMapStrategy strategy2 = new RandomMapStrategy();
        Tile[][] grid1 = strategy1.generate(15, 15);
        Tile[][] grid2 = strategy2.generate(15, 15);
        
        boolean different = false;
        for (int row = 0; row < grid1.length && !different; row++) {
            for (int col = 0; col < grid1[0].length && !different; col++) {
                if (grid1[row][col].getType() != grid2[row][col].getType()) {
                    different = true;
                }
            }
        }
        assertTrue(different, "Random maps should be different");
    }

    @Test
    public void testAllTilesAreValidTypes() {
        RandomMapStrategy strategy = new RandomMapStrategy();
        Tile[][] grid = strategy.generate(10, 10);
        
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                Tile.Type type = grid[row][col].getType();
                assertTrue(type == Tile.Type.GRASS || 
                          type == Tile.Type.STONE || 
                          type == Tile.Type.WATER || 
                          type == Tile.Type.CHEST);
            }
        }
    }
}
