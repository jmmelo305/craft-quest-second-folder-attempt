package CraftQuestApp;

import java.util.Scanner;

/* 
Programmer: Jose Melo
Program: Controller.java
Purpose: Controller for MVC Programs: Manages the game loop and player input
Date: April 25, 2026

*/
public class Controller {

    private World world;
    private Player player;
    private View view;
    private Scanner scanner;

    public Controller(World world, Player player, View view) {
        this.world   = world;
        this.player  = player;
        this.view    = view;
        this.scanner = new Scanner(System.in);
    }

    /** Starts the main game loop */
    public void start() {
        view.render(); // draw board before first move

        while (true) {
            System.out.print("\n  Your move >>> ");
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "w": player.move( 0, -1, world); break; // up
                case "s": player.move( 0,  1, world); break; // down
                case "a": player.move(-1,  0, world); break; // left
                case "d": player.move( 1,  0, world); break; // right
                case "q":
                    System.out.println("\n  Thanks for playing CraftQuest. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("  Unknown key. Use W/A/S/D to move, Q to quit.");
            }

            // Check win condition after every move
            if (world.allChestsCollected()) {
                view.render();
                System.out.println("\n  *** YOU WIN! All chests have been found! ***");
                System.out.println("  Final loot: " + player.getInventory().getItems());
                scanner.close();
                return;
            }
        }
    }
}
