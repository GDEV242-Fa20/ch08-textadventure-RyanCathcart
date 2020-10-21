import java.util.ArrayList;

/**
 * A class to serve as the player of the game.
 * Players have a name and room location.
 *
 * @author Ryan Cathcart
 * @version 2020.10.20
 */
public class Player{
    private String name;
    private Room currentRoom;
    private int maxWeight;
    private int currentWeight;
    private ArrayList<Item> backpack;

    /**
     * Constructor for objects of class Player
     * @param name          The name of the player.
     * @param initialRoom   The initial room of the player.
     */
    public Player(String name, Room initialRoom) {
        this.name = name;
        currentRoom = initialRoom;
        maxWeight = 5;
        currentWeight = 0;
        backpack = new ArrayList<Item>();
    }
    
    /**
     * Gets the player's current room.
     * @return The current room.
     */
    public Room getRoom() {
        return currentRoom;
    }
    
    /**
     * Sets the room for the player.
     * @param newRoom The new room.
     */
    public void setRoom(Room newRoom) {
        currentRoom = newRoom;
    }
    
    /**
     * Adds an item from the current room to the Player's backpack.
     * @param item The item to be added.
     */
    public void addItem(Item item) {
        int itemWeight = item.getWeight();
        if (maxWeight >= currentWeight + itemWeight) {
            currentRoom.removeItem(item);
            backpack.add(item);
            currentWeight += itemWeight;
            System.out.println(item.getDesc() + " obtained.");
        } else {
            System.out.println("You can't carry this item! Your backpack is already too full.");
        }
    }
    
    /**
     * Removes an item from the Player's backpack.
     * @param item The item to be removed.
     * @return if the item was removed
     */
    public boolean removeItem(Item item) {
        currentWeight -= item.getWeight();
        System.out.println(item.getDesc() + " dropped.");
        return backpack.remove(item);
    }
    
    /**
     * Return the first item found in the backpack with the 
     * specified description. If there is no item with that 
     * description, return null.
     * @param itemDesc The item's description.
     * @return The item with the specified description.
     */
    public Item getItem(String itemDesc) {
        for (int i=0;i<backpack.size();i++) {
            if (backpack.get(i).getDesc().equalsIgnoreCase(itemDesc)) {
                return backpack.get(i);
            }
        }
        return null;
    }
    
    public void equipItem(Item item) {
        backpack.remove(item);
        System.out.println(item.getDesc() + " equipped.");
        if (item.getDesc().equals("pendant")) {
            maxWeight += 5;
            System.out.println("Max weight capacity increased by 5.");
        }
    }
    
    /**
     * Prints the contents of the Player's backpack.
     */
    public void printBackpack() {
        if (backpack.size() > 0) {
            System.out.println("Item        Weight");
            for (int i=0;i<backpack.size();i++) {
                System.out.println(backpack.get(i).printItem());
            }
            
        } else {
            System.out.println("Your backpack is empty.");
        }
        System.out.println("Total weight: " + currentWeight + "/" + maxWeight);
    }
}
