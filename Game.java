/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
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
 * @version 2020.10.20
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
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
        townSquare = new Room("in the town's square: the middle of town");
        northPath = new Room("on the path north of the town square");
        eastPath = new Room("on the path east of the town square");
        southPath = new Room("on the path south of the town square");
        westPath = new Room("on the path west of the town square");
        castle = new Room("in the town castle");
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

        currentRoom = townSquare;  // start game in the town square
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
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
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
                
            case LOOK:
                look();
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
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
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
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    /**
     * Look around the room. Prints the current room's description.
     */
    private void look() {
        System.out.println(currentRoom.getLongDescription());
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
