
/**
 * A class that represents a non-player character in 
 * the game. The NPC has a name, a dialogue, 
 * and an item to give to the player.
 *
 * @author Ryan Cathcart
 * @version 2020.10.21
 */
public class NonPlayerCharacter {
    private String name;
    private String dialogue;
    private Item item;

    /**
     * Constructor for objects of class NonPlayerCharacter
     * 
     * @param  name      the NPC's name
     * @param  dialogue  what the NPC says when spoken to
     * @param  item      what item the NPC has
     */
    public NonPlayerCharacter(String name, String dialogue, Item item) {
        this.name = name;
        this.dialogue = dialogue;
        this.item = item;
    }
    
    /**
     * Gets this NPC's name
     * @return This NPC's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets this NPC's dialogue
     * @return This NPC's dialogue
     */
    public String getDialogue() {
        return dialogue;
    }
    
    /**
     * Prints NPC dialogue with NPC name before it to show who's speaking.
     */
    public void interact() {
        System.out.println(name + ": " + dialogue);
    }
    
    /**
     * Getss the item possessed by the NPC.
     * @return the item.
     */
    public Item getItem() {
        return item;
    }
    
    /**
     * Sets the item possessed by the NPC.
     * @param newItem The new item.
     */
    public void setItem(Item newItem) {
        item = newItem;
    }
}
