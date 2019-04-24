/*
 * Created by: Andrew Nguyen
 * Date: 2019-04-14
 * Time: 19:19
 * SURLY0
 */

import java.util.ArrayList;

/**
 * An Item object is created by the InsertParser.
 * Represents data to be inserted under a specified Relation type.
 */
public class Item {

    private String name;
    private ArrayList<String[]> attributes;

    /**
     * Constructor
     * @param name Item name; in this case it would be "CSCI141" or "GREGORY" for example
     * @param attributes Attributes of the Item. eg: [[27921 13:00 13:50 MWF CF115 JAGODZINSKI], ...]
     *                   Using a List of String[] since some Item can have multiple instances of
     *                   unique attributes. For example, under the OFFERING relation,
     *                   CSCI241 can be offered multiple times all under the CSCI241 Item.
     */
    public Item(String name, String[] attributes) {
        this.name = name;
        this.attributes = new ArrayList<>();
        this.attributes.add(attributes);
    }

    public String getName() {
        return name;
    }

    public ArrayList<String[]> getAttributes() {
        return attributes;
    }

    /**
     * Add a new set of attributes to the Item
     * @param attrSet
     */
    public void addAttributeSet(String[] attrSet) {
        attributes.add(attrSet);
    }

}
