import java.util.*;
import java.lang.*;

/**
 * Tuple class holds a LinkedList of AttributeValues.
 * So logically, if an Attribute class defines attributes for a relation,
 * the Tuple holds AttributeValues that give values according to that definition.
 * ie: if a Relation is [name, id, birthday] then tuple has [bob, 123, 01/01/2019]
 */
public class Tuple {

    private LinkedList<AttributeValue> values;

    /**
     * Constructor.
     * @param values Values should be passed in by the calling function,
     *               In this case it is the InsertParser
     */
    public Tuple(LinkedList<AttributeValue> values) {
        this.values = values;
    }

    /**
     * Utilizes a binary search as well. For reasoning see SurlyDatabase
     * @param attrName Attribute name
     * @return Returns a String of the attribute's value. NOT the AttributeValue obj
     */
    public String getValue(String attrName) {
        //needs to search the Tuple's List of AttributeValues for
        //A given attribute name, and return that value
        return valueBinarySearch(attrName, 0, values.size());
    }

    public AttributeValue getAttrValue(String attrName) {
        return attributeValueBinarySearch(attrName, 0, values.size());
    }

    public final LinkedList<AttributeValue> getValueList() {
        return values;
    }

    /**
     * re assign the List of Attribute Values. Used when performing intersections of Relations
     * like in Join, Select, and Delete parsers
     */
    public void setValues(LinkedList<AttributeValue> lst) {
        values = lst;
    }

    /**
     * For use with the JoinParser
     * @param oldname The original name, like CNUM
     * @param newName new name, eg: COURSE.CNUM
     */
    public void setAttributeValueName(String oldname, String newName) {
        AttributeValue val = getAttrValue(oldname);
        if (val == null) {
            return;
        }
        val.setName(newName);
    }

    /**
     * The binary search behind getValue()
     * @param name desired target attribute name
     * @param start 0
     * @param end values.size() - 1
     * @return Returns the attribute values as a String to getValue()
     */
    private String valueBinarySearch(String name, int start, int end) {
        if (start == end) {
            return null;
        }

        int mid = (start + end) / 2;
        if (values.get(mid).getName().equals(name)) {
            return values.get(mid).getValue();
        } else if (start < mid && mid < end) {
            String left = valueBinarySearch(name, start, mid);
            String right = valueBinarySearch(name, mid, end);
            if (left != null) {
                return left;
            } else {
                return right;
            }
        }
        return null;
    }

    /**
     * A binary search just like the one above but returns the AttributeValue object
     */
    public AttributeValue attributeValueBinarySearch(String name, int start, int end) {
        if (start == end) {
            return null;
        }

        int mid = (start + end) / 2;
        if (values.get(mid).getName().equals(name)) {
            return values.get(mid);
        } else if (start < mid && mid < end) {
             AttributeValue left = attributeValueBinarySearch(name, start, mid);
             AttributeValue right = attributeValueBinarySearch(name, mid, end);
            if (left != null) {
                return left;
            } else {
                return right;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return values.toString();
    }
}
