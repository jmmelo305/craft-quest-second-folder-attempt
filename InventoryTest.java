/*
Programmer: Jose Melo
Program: InventoryTest.java
Purpose: JUnit Tests for Inventory system
Date: April 28, 2026
*/

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    @Test
    public void testNewInventoryIsEmpty() {
        Inventory inv = new Inventory();
        assertEquals(0, inv.getItemCount());
    }

    @Test
    public void testAddSingleItem() {
        Inventory inv = new Inventory();
        inv.addItem("Diamond");
        assertEquals(1, inv.getItemCount());
    }

    @Test
    public void testAddMultipleItems() {
        Inventory inv = new Inventory();
        inv.addItem("Diamond");
        inv.addItem("Gold");
        inv.addItem("Iron");
        assertEquals(3, inv.getItemCount());
    }

    @Test
    public void testItemsListContainsAddedItem() {
        Inventory inv = new Inventory();
        inv.addItem("Diamond");
        assertTrue(inv.getItems().contains("Diamond"));
    }

    @Test
    public void testNullItemThrowsException() {
        Inventory inv = new Inventory();
        assertThrows(IllegalArgumentException.class, () -> inv.addItem(null));
    }
}
