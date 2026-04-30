

/*
Programmer: Jose Melo
Program: View.java
Purpose: Implements GameObserver and works with the visual outputs of the terminal for the game.
Date: April 26, 2026
*/


public class View implements GameObserver {

    private World world;
    private Player player;

    public View(World world, Player player) {
        this.world  = world;
        this.player = player;
    }

    
    // Called automatically by Player via the Observer pattern
    // every time the player moves or collects loot.
     
    @Override
    public void onGameUpdated() {
        render();
    }

    /* Prints the full tile grid and HUD to the console */
    public void render() {
        System.out.println("\n==================== CraftQuest ====================");
        System.out.println("  . Grass   # Stone   ~ Water   C Chest   P You\n");

        for (int row = 0; row < world.getHeight(); row++) {
            System.out.print("  ");
            for (int col = 0; col < world.getWidth(); col++) {
                if (col == player.getX() && row == player.getY())
                    System.out.print("P ");
                else
                    System.out.print(world.getTile(col, row).toSymbol() + " ");
            }
            System.out.println();
        }

        System.out.println("\n  Chests found : " + player.getInventory().getItemCount());
        System.out.println("  Loot         : " + player.getInventory().getItems());
        System.out.println("\n  W = Up   S = Down   A = Left   D = Right   Q = Quit");
        System.out.println("=====================================================");
    }
}
