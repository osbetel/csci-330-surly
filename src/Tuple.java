import java.util.*;
import java.io.*;
import java.lang.*;

public class Tuple{

    private LinkedList<AttributeValue> values;

    public Tuple(LinkedList<AttributeValue> values){
        this.values = values;
    }

    public String getValue(String attrName){
        //needs to search the Tuple's List of AttributeValues for
        //A given attribute name, and return that value
        return valueBinarySearch(attrName, 0, values.size() - 1);
    }

    private String valueBinarySearch(String name, int start, int end) {

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

}
