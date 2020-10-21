
/**
 * A class to represent an obtainable item that has
 * a description and arbitrary weight.
 *
 * @author Ryan Cathcart
 * @version 2020.10.20
 */
public class Item {
    private String desc;
    private int weight;

    /**
     * Constructor for objects of class Item
     * @param desc   the description of this Item
     * @param weight the weight of this Item
     */
    public Item(String desc, int weight) {
        this.desc = desc;
        this.weight = weight;
    }
    
    /**
     * Get the description of this Item.
     * @return This Item's description.
     */
    public String getDesc() {
        return desc;
    }
    
    /**
     * Get the weight of this Item.
     * @return This Item's weight.
     */
    public int getWeight() {
        return weight;
    }
    
    /**
     * Get the description of this item.
     * 
     * @return the description and weight
     */
    public String printItem() {
        return desc + "...weight: " + weight;
    }
}
