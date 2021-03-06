import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Ryan Cathcart
 * @version 2020.10.21
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> items;
    private ArrayList<NonPlayerCharacter> npcs;
    private boolean locked;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard". This room's item is initialized to null.
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<Item>();
        npcs = new ArrayList<NonPlayerCharacter>();
        locked = false;
    }
    
    /**
     * Create a room described "description" and containing an item. 
     * Initially, it has no exits. "description" is something 
     * like "a kitchen" or "an open court yard".
     * @param description The room's description.
     * @param item        The item found in this room.
     */
    public Room(String description, Item item) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<Item>();
        items.add(item);
        npcs = new ArrayList<NonPlayerCharacter>();
    }
    
    /**
     * Create a room described "description" and containing an item. 
     * Initially, it has no exits. "description" is something 
     * like "a kitchen" or "an open court yard".
     * Using this constructor allows the room to be LOCKED.
     * 
     * @param description The room's description.
     * @param locked      If the room is locked.
     */
    public Room(String description, boolean locked) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<Item>();
        npcs = new ArrayList<NonPlayerCharacter>();
        this.locked = locked;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    /**
     * Add an NPC to this Room.
     * @param npc the NPC to be added.
     */
    public void addNPC(NonPlayerCharacter npc) {
        npcs.add(npc);
    }
    
    /**
     * Add an Item to this Room.
     * @param item the Item to be added.
     */
    public void addItem(Item item) {
        items.add(item);
    }
    
    /**
     * Removes an Item from this Room.
     * @param   item the Item to be removed.
     * @return  Whether or not the item was removed.
     */
    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        String desc = "You are " + description + ".\nArea Items: ";
        if (items.size() == 0) {                    // print the items in the area
            desc += "This area has no items.";
        } else {
            for (Item item : items) {
                desc += item.printItem() + ", ";
            }
        }
        desc += "\nArea NPCs: ";
        if (npcs.size() == 0) {                     // print the npcs in the area
            desc += "This area has no NPCs.";
        } else {
            for (NonPlayerCharacter npc : npcs) {
                desc += npc.getName() + ", ";
            } 
        }
        desc += "\n" + getExitString();
        return desc;
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Return the first item found with the specified description. 
     * If there is no item with that description, return null.
     * @param itemDesc The item's description.
     * @return The item with the specified description.
     */
    public Item getItem(String itemDesc) {
        for (int i=0;i<items.size();i++) {
            if (items.get(i).getDesc().equalsIgnoreCase(itemDesc)) {
                return items.get(i);
            }
        }
        return null;
    }
    
    /**
     * Return the first NPC found with the specified name. 
     * If there is no NPC with that name, return null.
     * @param npcName The NPC's name.
     * @return The NPC with the specified name.
     */
    public NonPlayerCharacter getNPC(String npcName) {
        for (int i=0;i<npcs.size();i++) {
            if (npcs.get(i).getName().equalsIgnoreCase(npcName)) {
                return npcs.get(i);
            }
        }
        return null;
    }
    
    /**
     * Returns whether or not this Room is locked.
     * @return if the room is locked.
     */
    public boolean isLocked() {
        return locked;
    }
    
    /**
     * Unlocks the room.
     */
    public void unlock() {
        locked = false;
    }
}
