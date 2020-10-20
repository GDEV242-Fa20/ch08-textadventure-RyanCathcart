
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
     * Get the description of this item.
     * 
     * @return the description and weight
     */
    public String printItem() {
        return desc + ", weight: " + weight;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public int sampleMethod(int y) {
        // put your code here
        return y;
    }
}
