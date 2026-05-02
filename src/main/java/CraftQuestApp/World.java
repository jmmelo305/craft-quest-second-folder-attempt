package CraftQuestApp;

/*
Programmer: Melo
Program: Model File for CraftQuest
Purpose: Model file - part of the MVC of the project
         stores and manage's the game's data and logic
Date: April 23, 2026
*/

import java.util.*;

// Any class that implements GameObserver will include
// onGameUpdated(), the View.java file implements this later on.
interface GameObserver {
    void onGameUpdated();
}

// The purpose of this method is that any class that implements
// Map Strategy will create the 2D array of Tile objects - Two maps created will use this.
interface MapStrategy {
    Tile[][] generate (int width, int height);
}

// Creates Tile class, by using an enum (w3schools.com/java/java_enums.asp) 
// it allows for the list of options to only be Grass, Stone, Water & Chests. 
// Only these options, no need to worry about numbers or strings
class Tile {
    enum Type {GRASS, STONE, WATER, CHEST}

    // Stores what kind of tile Type (G, S, W, C) it is
    private Type type; 

    // Has this chest been collected or not (T or F)
    private boolean collected;

    // Stores the unique loot inside this chest
    private String chestLoot;


    // Constructor for Tile class 
    Tile(Type type){
        this.type = type;
        this.collected = false;
    }

    // Get Methods
    Type getType(){
        return type;
    }
    boolean isCollected (){ 
        return collected; 
    }

    // The player can walk on all the tiles except water, so this method is created 
    // make water unwalkable
    boolean isWalkable(){
        return type != Type.WATER;
    }

    // Method to check if the tile is collectible only if it's a CHEST and it hasn't been collected yet.
    // && means that both conditions must be true, and the !collected means that collected is false. 
    boolean isCollectible(){
        return type == Type.CHEST && !collected; 
    }

    // method that gets called when the play steps on a chest.
    void collect (){
        if (type == Type.CHEST){
            collected = true; 
        }
    }

    // Set the loot for this chest (only used when creating chests)
    void setChestLoot(String loot){
        if (type == Type.CHEST){
            this.chestLoot = loot;
        }
    }

    // Get the loot from this chest
    String getChestLoot(){
        return chestLoot;
    }
    
    // Switch case to draw the corresponding tile on the screen
    // depending on the tile type
    // collected  ? "o" : "C"
    // represents that if the chest has been collected it returns "o", but if it hasn't it returns "C"
    String toSymbol(){
        switch(type){
            case GRASS: return ".";
            case STONE: return "#";
            case WATER: return "~";
            case CHEST: return collected ? "o" : "C";
            default:    return "?";
        }
    }
}

class Inventory {
// The only job of this class is to store a list of the items the player has collected. 
    
// List to store items, only strings. 
    private List <String> items;

    // Constructor 
    Inventory (){ 
        this.items = new ArrayList<>();
    }


    // Created a null item exception error - Error Handling
    void addItem(String item){
        if (item == null){
            throw new IllegalArgumentException("Item cannot be null");
        }
        items. add(item);
    }

    // getters
    List<String> getItems() {
        return items;
    }
    int getItemCount(){
        return items.size();
    }
}


// Class made to track player's position on the grid, holds their inventory,
// and notifies the View everytime something changes
class Player{
    // variables for this class.
    private int x;
    private int y;

    private Inventory inventory;
    private List<GameObserver> observers; 


    // Constructor
    Player(int startX, int startY){
        this.x = startX;
        this.y = startY;
        this.inventory = new Inventory();
        this.observers = new ArrayList<>();
    }

    // two methods that run the entire observer pattern on the player side
    void addObserver (GameObserver observer){
        observers.add(observer);
    }
    private void notifyObservers(){
        for (GameObserver o: observers){
            o.onGameUpdated();
        }
    }

    // method that focuses on movements in the game. 
    // Returns true if move succeeded, false if blocked
    boolean move (int dx, int dy, World world){
        // initializing variables
        int newX; 
        int newY;
        newX = x + dx;
        newY = y + dy;

        // Blocks movement into non - walkable tiles
        if (newX < 0 || newX >= world.getWidth())
            return false;
        if (newY < 0 || newY >= world.getHeight()) 
            return false;

        // !target.isWalkable() means if if the targetted tile IS NOT walkable then it prevents the movement into it.
        Tile target = world.getTile(newX, newY);
        if (!target.isWalkable()) return false; 

        // Move is valid, updates position
        x = newX;
        y = newY; 

        // Auto collects a chest if the player lands on one
        if (target.isCollectible()){
            String loot = target.getChestLoot();
            target.collect();
            inventory.addItem(loot != null ? loot : "Mystery Loot");
        }
        // tells the View something has changed. 
        notifyObservers();
        return true;
    }

    // getters
    int getX (){
        return x;
    }

    int getY (){
        return y;
    }
    Inventory getInventory(){
        return inventory;
    }
}

class FixedMapStrategy implements MapStrategy {

    @Override
    public Tile[][] generate(int width, int height) {
        Tile[][] grid = new Tile[height][width];

        // Fill everything with GRASS
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                grid[row][col] = new Tile(Tile.Type.GRASS);

        // Water ponds
        grid[3][3] = new Tile(Tile.Type.WATER);
        grid[3][4] = new Tile(Tile.Type.WATER);
        grid[4][3] = new Tile(Tile.Type.WATER);
        grid[4][4] = new Tile(Tile.Type.WATER);

        // Stone patches
        grid[2][2] = new Tile(Tile.Type.STONE);
        grid[2][3] = new Tile(Tile.Type.STONE);
        grid[6][7] = new Tile(Tile.Type.STONE);
        grid[7][7] = new Tile(Tile.Type.STONE);

        // Three chests to find - each with unique loot
        Tile chest1 = new Tile(Tile.Type.CHEST);
        chest1.setChestLoot("Diamond");
        grid[1][8] = chest1;

        Tile chest2 = new Tile(Tile.Type.CHEST);
        chest2.setChestLoot("Iron");
        grid[5][6] = chest2;

        Tile chest3 = new Tile(Tile.Type.CHEST);
        chest3.setChestLoot("Gold");
        grid[8][1] = chest3;

        return grid;
    }
}


// RANDOM MAP STRATEGY — Strategy Design Pattern
// Generates a different map every time the game is played.

class RandomMapStrategy implements MapStrategy {

    @Override
    public Tile[][] generate(int width, int height) {
        Tile[][] grid = new Tile[height][width];
        Random rand = new Random();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int roll = rand.nextInt(100);
                if      (roll < 10) grid[row][col] = new Tile(Tile.Type.WATER);
                else if (roll < 25) grid[row][col] = new Tile(Tile.Type.STONE);
                else                grid[row][col] = new Tile(Tile.Type.GRASS);
            }
        }

        // Place exactly 3 chests on random GRASS tiles - each with unique loot
        String[] lootOptions = {"Diamonds", "Iron", "Gold"};
        int chestsPlaced = 0;
        while (chestsPlaced < 3) {
            int row = rand.nextInt(height);
            int col = rand.nextInt(width);
            if (grid[row][col].getType() == Tile.Type.GRASS) {
                Tile chest = new Tile(Tile.Type.CHEST);
                chest.setChestLoot(lootOptions[chestsPlaced]);
                grid[row][col] = chest;
                chestsPlaced++;
            }
        }

        // Always keep spawn point (0,0) clear
        grid[0][0] = new Tile(Tile.Type.GRASS);
        return grid;
    }
}

// WORLD — holds the tile grid, exposes access + win condition
// Uses a MapStrategy to generate its grid at construction.
// Does not care HOW the map is built — just that it gets one.
public class World {

    private Tile[][] grid;
    private int width, height;

    public World(int width, int height, MapStrategy strategy) {
        this.width  = width;
        this.height = height;
        this.grid   = strategy.generate(width, height);
    }

    public Tile getTile(int x, int y) { return grid[y][x]; }
    public int getWidth()             { return width; }
    public int getHeight()            { return height; }

    /** Returns true only when every chest on the map is collected */
    public boolean allChestsCollected() {
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++) {
                Tile t = grid[row][col];
                if (t.getType() == Tile.Type.CHEST && !t.isCollected())
                    return false;
        }
        return true;
    }
}
