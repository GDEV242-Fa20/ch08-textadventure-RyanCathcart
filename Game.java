import java.util.ArrayList;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. 
 * 
 *  To play this game, create an instance of this class in BlueJ and call the "play"
 *  method, or execute the main method in this class.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *  
 *  STUDENT NOTE:
 *  The intended layout of the rooms is depicted in the map.jpg file
 *  found in this project's GitHub repository.
 * 
 * @author  Ryan Cathcart
 * @version 2020.10.21
 */

public class Game 
{
    private Parser parser;
    private Player player;
    private ArrayList<Room> path;
    
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        path = new ArrayList<Room>();
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room townSquare, northPath, eastPath, southPath, westPath, 
        castle, church, blacksmith, mines, apothecary, 
        inn, forest, bank, market, barbVillage;
      
        // create the rooms
        townSquare = new Room("in the town's square: the middle of town", new Item("key", 1));
        northPath = new Room("on the path north of the town square");
        eastPath = new Room("on the path east of the town square");
        southPath = new Room("on the path south of the town square");
        westPath = new Room("on the path west of the town square");
        castle = new Room("in the town castle", true); // The church starts locked
        church = new Room("in the town church");
        blacksmith = new Room("in the town blacksmith");
        mines = new Room("in the town mines");
        apothecary = new Room("in the town apothecary");
        inn = new Room("in the town inn");
        forest = new Room("in the forest south of town");
        bank = new Room("in the town bank");
        market = new Room("in the town market");
        barbVillage = new Room("in the barbarian village west of town");
        
        // initialise room exits
        townSquare.setExit("north", northPath);
        townSquare.setExit("east", eastPath);
        townSquare.setExit("south", southPath);
        townSquare.setExit("west", westPath);

        northPath.setExit("east", church);
        northPath.setExit("south", townSquare);
        northPath.setExit("west", castle);
        
        eastPath.setExit("north", blacksmith);
        eastPath.setExit("south", mines);
        eastPath.setExit("west", townSquare);
        
        southPath.setExit("north", townSquare);
        southPath.setExit("east", inn);
        southPath.setExit("south", forest);
        southPath.setExit("west", apothecary);
        
        westPath.setExit("north", market);
        westPath.setExit("east", townSquare);
        westPath.setExit("south", bank);
        westPath.setExit("west", barbVillage);
        
        castle.setExit("east", northPath);
        
        church.setExit("west", northPath);
        
        blacksmith.setExit("south", eastPath);
        
        mines.setExit("north", eastPath);
        
        apothecary.setExit("east", southPath);
        
        inn.setExit("west", southPath);
        
        forest.setExit("north", southPath);
        
        bank.setExit("north", westPath);
        
        market.setExit("south", westPath);
        
        barbVillage.setExit("east", westPath);
        
        // Initial NPCs with their items
        castle.addNPC(new NonPlayerCharacter("King", "Please take this amulet to let you hold more items.", 
                                             new Item("pendant", 0)));
        market.addNPC(new NonPlayerCharacter("Trader1", "Take this sword.", new Item("sword", 2)));
        market.addNPC(new NonPlayerCharacter("Trader2", "Take this apple.", new Item("apple", 1)));
        barbVillage.addNPC(new NonPlayerCharacter("Barbarian", "Don't come to these lands! Return at once! *Swings axe*", null));
                                             
        // Initialize items in rooms
        forest.addItem(new Item("apple", 1));
        forest.addItem(new Item("stick", 2));
        
        player = new Player("Bob", townSquare); // Create player and start them in the town square
        path.add(townSquare);                   // Add the starting room to the path list.
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            System.out.println();
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredible adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println("---------------------------------------------------------");
        System.out.println();
        System.out.println(player.getRoom().getLongDescription());
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;
                
            case UNLOCK:
                unlock(command);
                break;
                
            case LOOK:
                look();
                break;
                
            case TAKE:
                take(command);
                break;
                
            case DROP:
                drop(command);
                break;
                
            case ITEMS:
                player.printBackpack();
                break;
                
            case EAT:
                eat();
                break;
                
            case TALK:
                wantToQuit = talk(command);
                break;
                
            case EQUIP:
                equip(command);
                break;
                
            case BACK:
                back();
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You find yourself in the territory");
        System.out.println("of a medieval village. Where to?\n");
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            if (nextRoom.isLocked()) {
                System.out.println("This room is locked!");
            } else {
                player.setRoom(nextRoom);
                path.add(nextRoom);
                System.out.println(player.getRoom().getLongDescription());
            }
        }
    }
    
    /** 
     * Try to unlock the room in one direction, otherwise print an error message.
     */
    private void unlock(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Unlock where? (Second word must be one of the exits)");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room room = player.getRoom().getExit(direction);

        if (room == null) {
            System.out.println("There is nothing to unluck there!");
        } else {
            if (room.isLocked() && player.getItem("key") != null) {
                room.unlock();
                player.removeItem(player.getItem("key"));
                System.out.println("The area has been unlocked!");
            } else if (room.isLocked()) {
                System.out.println("You don't have a key!");
            } else {
                System.out.println("This room is already unlocked!");
            }
        }
    }
    
    /**
     * Look around the room. Prints the current room's description.
     */
    private void look() {
        System.out.println(player.getRoom().getLongDescription());
    }
    
    /**
     * Try to take the specified item from the room and put it
     * in the player's backpack.
     * @param command The user-entered command.
     */
    private void take(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("Take what?");
            return;
        }
        
        String itemName = command.getSecondWord();
        
        // Try to take item.
        Item newItem = player.getRoom().getItem(itemName);
        
        if (newItem == null) {
            System.out.println("That item doesn't exist! Check spelling.");
        } else {
            player.addItem(newItem);
        }
    }
    
    /**
     * Try to take the specified item from the Player's backpack
     * and put it in the room.
     * @param command The user-entered command.
     */
    private void drop(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Drop what?");
            return;
        }
        
        String itemName = command.getSecondWord();
        
        // Try to take item.
        Item newItem = player.getItem(itemName);
        
        
        if (newItem == null) {
            System.out.println("That item doesn't exist! Check spelling.");
        } else {
            player.removeItem(newItem);
            player.getRoom().addItem(newItem);
            System.out.println(newItem.getDesc() + " dropped.");
            // maybe print backpack with new item in it?
        }
    }
    
    /**
     * Eat food. Currently the player always has food.
     */
    private void eat() {
        player.eat();
        System.out.println("You have eaten now and you are not hungry any more.");
    }
    
    /**
     * Equip target item.
     * @param command The user-entered command. 
     */
    private void equip(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to equip...
            System.out.println("Equip what?");
            return;
        }
        
        String itemName = command.getSecondWord();
        
        // Try to equip item.
        Item newItem = player.getItem(itemName);
        
        if (newItem == null) {
            System.out.println("That item doesn't exist! Check spelling.");
        } else {
            player.equipItem(newItem);
        }
        
    }
    
    /**
     * Talk to target NPC.
     * @param command The user-entered command. 
     */
    private boolean talk(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know who to talk to...
            System.out.println("Talk to who?");
            return false;
        }
        
        String npcName = command.getSecondWord();
        
        // Try to talk to NPC.        
        if (player.getRoom().getNPC(npcName) == null) {
            System.out.println("That NPC doesn't exist! Check spelling.");
        } else {
            player.getRoom().getNPC(npcName).interact();
            if (npcName.equalsIgnoreCase("barbarian"))
                if(player.damage(3))
                    return true;
            if (player.getRoom().getNPC(npcName).getItem() != null) {
                player.addItem(player.getRoom().getNPC(npcName).getItem());
                player.getRoom().getNPC(npcName).setItem(null);
            }
        }
        return false;
    }
    
    /**
     * Go back 1 step.
     */
    private void back() {
        if (path.size() > 1) 
            path.remove(path.size() - 1);
        else
            System.out.println("Can't go back anymore!");
            
        // Try to leave current room.
        Room nextRoom = path.get(path.size() - 1);
        
        player.setRoom(nextRoom);
        System.out.println(player.getRoom().getLongDescription());
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
