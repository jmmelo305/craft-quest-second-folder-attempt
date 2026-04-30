/*
Programmer: Jose Melo
Program: TileTest.java
Purpose: JUnit Tests for tile types and ability to walk or collect items on the tiles
Date: April 28, 2026
*/


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TileTest {

    @Test
    public void testGrassIsWalkable() {
        Tile t = new Tile(Tile.Type.GRASS);
        assertTrue(t.isWalkable());
    }

    @Test
    public void testStoneIsWalkable() {
        Tile t = new Tile(Tile.Type.STONE);
        assertTrue(t.isWalkable());
    }

    @Test
    public void testWaterIsNotWalkable() {
        Tile t = new Tile(Tile.Type.WATER);
        assertFalse(t.isWalkable());
    }

    @Test
    public void testChestIsCollectibleBeforeCollection() {
        Tile t = new Tile(Tile.Type.CHEST);
        assertTrue(t.isCollectible());
    }

    @Test
    public void testChestNotCollectibleAfterCollection() {
        Tile t = new Tile(Tile.Type.CHEST);
        t.collect();
        assertFalse(t.isCollectible());
    }

    @Test
    public void testCollectSetsCollectedFlag() {
        Tile t = new Tile(Tile.Type.CHEST);
        t.collect();
        assertTrue(t.isCollected());
    }

    @Test
    public void testGrassIsNotCollectible() {
        Tile t = new Tile(Tile.Type.GRASS);
        assertFalse(t.isCollectible());
    }
}
