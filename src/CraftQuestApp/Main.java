package CraftQuestApp;
import java.util.Scanner;


// Main File: Responsible for starting CraftQuest program
// Responsibilities:
//   1. Ask player to choose a map type (Strategy pattern)
//   2. Construct the Model (World + Player)
//   3. Construct the View
//   4. Register the View as an Observer of the Player
//   5. Hand control to the Controller
//


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=============================================");
        System.out.println("          Welcome to CraftQuest!            ");
        System.out.println("  A Minecraft-inspired console explorer     ");
        System.out.println("=============================================");
        System.out.println("  Choose your map type:");
        System.out.println("    1 - Fixed map  (same layout every time)");
        System.out.println("    2 - Random map (new world every time)  ");
        System.out.print("  >>> ");

        String choice = scanner.nextLine().trim();
        scanner.close();

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
