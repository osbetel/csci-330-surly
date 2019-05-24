import java.lang.*;
import java.util.Arrays;

/**
 * The logical counterpart to Attribute.
 * If something has an Attribute, that Attribute should have a Value...
 */
public class AttributeValue{
    private String name;
    private String value;

    public AttributeValue(String name, String value){
        this.name = name;
        this.value = value;
    }

    public String getName(){
        return name;
    }

    public String getValue(){
        return value;
    }

    @Override
    public String toString() {
        String[] s = new String[2];
        s[0] = name; s[1] = value;
        return Arrays.toString(s);
    }

}
