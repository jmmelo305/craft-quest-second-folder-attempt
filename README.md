# Craft Quest 

## Summary:
#### Craft Quest is a 2D game, that runs on the terminal, focused on traveling across a tiled map to complete the goal of collecting all the chests on the map. Players are allowed to cross over Grass ("."), Stone ("#"), Chests ("C") but not Water ("~"). Once a chest has been collected, the tile changes from ("C") to ("O"). 

### How to Run!
#### Requirements:
- Java JDK 21+
- Apache Maven 3.6+
- Terminal / Command Prompt Access 
#### Windows:
1. Make sure You have Java Development Kit installed 
2. Verify you download with ```java --version```
3. Download Maven from Apache Maven
4. Extract to ```C:\Program Files\Apache\maven```
5. Add to path: ```C:\Program Files\Apache\maven\bin```
6. Verify: ```mvn -version```
7. Download ALL CraftQuest source code
8. Open the terminal / command prompt in Visual Studio Code with the source code folder open. 
9. Run the Game: ```mvn clean compile package -DskipTests -q && java -cp target/classes CraftQuestApp.Main```


#### Mac: 
1. Open terminal in Visual Studio Code 
2. Install Homebrew if not present: ```/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"```
3. Install JDK: ```brew install openjdk@21```
4. Verify Installation: ```java --version```
5. Instal Apache Maven: ```brew install maven```
6. Verify installation: ```mvn -version```
7. Download ALL CraftQuest source code
8. Open the terminal / command prompt in Visual Studio Code with the source code folder open. 
9. Run the Game: ```mvn clean compile package -DskipTests -q && java -cp target/classes CraftQuestApp.Main```


## Overview of MVC Structure

```
CraftQuestApp/
├── pom.xml
├── README.md
└── src/
    ├── main/java/com/craftquest/
    │   ├── Main.java
    │   ├── model/
    │   │   ├── Tile.java
    │   │   ├── Inventory.java
    │   │   ├── Player.java
    │   │   ├── World.java
    │   │   ├── GameObserver.java
    │   │   ├── MapStrategy.java
    │   │   ├── MapOneStrategy.java
    │   │   └── MapTwoStrategy.java
    │   ├── view/
    │   │   └── GameView.java
    │   └── controller/
    │       └── GameController.java
    └── test/java/com/craftquest/
        ├── TileTest.java
        ├── InventoryTest.java
        ├── PlayerTest.java
        └── WorldTest.java
```

## Design Patterns Implemented. 

### Observer:
The observer pattern uses a one-to-many relationship between objects. When one object changes state, all its dependents (or observers) are automatically notified and updated. 
#### Used in: 
- GameObserver (Interface)
- Player (Subject)
- ```View.java``` file as observer

#### Why this design?
I chose this design because it helps automatically update the UI. It updates the programs output as it updates (player movement, the game loot being collected), almost immediately. 


### Strategy 
The strategy pattern is what allows for random maps to be generated efficiently and accurately. By using the strategy pattern, I was able to implement different algorithms to be able to create different map generation options. The pattern itself provides the interface ```MapStrategy``` that both map types (random and fixed) must implement. 

Dumbed - down version  
- You want to make a map, you want different designs of the map, but you don't want to learn how to make each design possible. So with the Strategy Pattern you just tell it that you want a random map. It goes through the steps to make a map (```FixedMapStrategy``` and ```RandomMapStrategy```) and picks the random map step by step list. Then it creates the random map using those steps. 

#### Used in:
- ```MapStrategy``` (Interface)
- ```FixedMapStrategy```
- ```RandomMapStrategy```

#### Why this design?
This design pattern allows for a wide variety of random maps to be generated without having to hard-code it into the program itself. 

## AI Use:
### The AI's involved in this program were: ClaudeAI

##### I fed a previous personal project idea into Claude AI that then helped me, by asking me follow up questions of what I wanted my program to be, come up with this project. 

##### I asked it clarifying questions for the Observer and Strategy and how (logically) to implement it into the game itself. 

##### I also used ClaudeAI to help me figure out the issue I was having with the file paths (explains why I have another Repo listed).

##### I also used ClaudeAI to help me get Maven running on macOS. Claude provided me with the commands necessary to run the JUnit tests and CraftQuest in the terminal. 

## Known Issues:
- Chest count is limited to only 3 chests per map. (This is done on purpose to keep the program simple)