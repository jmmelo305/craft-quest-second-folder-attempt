package CraftQuestApp;
import java.util.Scanner;

/*
Programmer: Jose Melo
Program: Main.java
Purpose: The main file responsible for starting the CraftQuest program.
Date: 4/28/2026
*/

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=============================================");
        System.out.println("          Welcome to CraftQuest!            ");
        System.out.println("  A Minecraft-inspired console explorer     ");
        System.out.println("=============================================");
        System.out.println("            How to play:                    ");
        System.out.println("  - Use W, A, S, D to move                 ");
        System.out.println("  - Press Q to quit                        ");
        System.out.println("  - Find chests to collect loot            ");
        System.out.println("  - Explore the world and collect all chests to win!        ");
        System.out.println("  - Press Enter after each move to continue");
        System.out.println("=============================================");
        System.out.println(" Press E to continue! ");
        System.out.println(" Press Q to quit! ");
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("e")) {
                break;
            }
            if (input.equals("q")) {
                System.out.println("\n  Thanks for playing CraftQuest. Goodbye!");
                scanner.close();
                return;
            }
            System.out.println("  Invalid input. Please press E to continue or Q to quit.");
        }

        System.out.println("  Choose your map type:");
        System.out.println("    1 - Fixed map  (same layout every time)");
        System.out.println("    2 - Random map (new world every time)  ");
        System.out.println("    Q - Quit");
        System.out.print("  >>> ");

        String choice;
        while (true) {
            choice = scanner.nextLine().trim().toLowerCase();
            
            if (choice.equals("q")) {
                System.out.println("\n  Thanks for playing CraftQuest. Goodbye!");
                scanner.close();
                return;
            }
            
            if (choice.equals("1") || choice.equals("2")) {
                break;
            }
            
            System.out.println("  Invalid input. Please enter 1, 2, or Q to quit.");
            System.out.print("  >>> ");
        }
        // Note: Don't close scanner here - Controller needs System.in for game input
        
        // --- Strategy Pattern ---
        // Swap the map generation algorithm at runtime
        // based on player's choice — no if/else inside World needed.
        MapStrategy strategy;
        if (choice.equals("2")) {
            strategy = new RandomMapStrategy();
            System.out.println("\n  Generating a random world...\n");
        } else {
            strategy = new FixedMapStrategy();
            System.out.println("\n  Loading fixed world...\n");
        }

        // --- Wire up MVC ---
        World  world  = new World(10, 10, strategy); // Model
        Player player = new Player(0, 0);            // Model
        View   view   = new View(world, player);     // View

        // --- Observer Pattern ---
        // Register the View so Player notifies it automatically
        // whenever the game state changes.
        player.addObserver(view);

        // --- Hand off to Controller ---
        Controller controller = new Controller(world, player, view);
        controller.start();
    }
}
