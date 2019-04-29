import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Handles INSERT commands as they come up from the input text file.
 */
public class InsertParser {

//    Format for input
//    #INSERTING TUPLES
//    INSERT COURSE CSCI141 'COMPUTER PROGRAMMING I' 4;
//    INSERT COURSE CSCI145 'COMP PROG & LINEAR DATA STRUCTURES' 4;

    private String input;           //raw line of input
    private String relationType;    //as in COURSE, PREREQ, etc.
    private String[] attrArr;

    public InsertParser(String input) {
        this.input = input;
    }

    public String parseRelationName() {
        if (relationType == null) {
            relationType = input.substring(ordinalIndexOf(" ", 1) + 1, ordinalIndexOf(" ", 2));
        }
        return relationType;
    }

    /**
     * Determines how many attributes a given item (Relation or Tuple to INSERT) has.
     * @return integer of the number of attributes.
     */
    public int parseAttributeCount() {
        String attr = input.substring(ordinalIndexOf(" ", 2) + 1);
        attributesToArray(attr);
        return attrArr.length;
    }

    /**
     * Generates and returns a tuple from the input string
     * @param schema Lexical analyzer should input a schema such that we can construct a list of values
     * @return Tuple object
     */
    public Tuple generateTuple(LinkedList<Attribute> schema) {
        LinkedList<AttributeValue> attrValueList = new LinkedList<>();

        if (attrArr.length == schema.size()) {
            for (int i = 0; i < attrArr.length; i++) {
                String name = schema.get(i).getName();
                String value = attrArr[i];
                AttributeValue attrToInsert = new AttributeValue(name, value);
                attrValueList.add(attrToInsert);
            }
        }
        return new Tuple(attrValueList);
    }

    /**
     * Takes the raw String form of the attributes like "(CSCI141 'COMPUTER PROGRAMMING I' 4)"
     * and converts it into an array: [CSCI141, COMPUTER PROGRAMMING I, 4]
     * @param attr attribute string
     */
    private void attributesToArray(String attr) {
        /*
         * Needs to consume a string, putting all symbols into an array in sequential order,
         * where items in-between single quotes are considered single symbols
         */
        if (!attr.contains("'")) {
            attrArr = attr.split(" ");
        } else {
            ArrayList<String> temp = new ArrayList<>();
            Scanner sc = new Scanner(attr);
            String s;
            while (sc.hasNext()) {
                s = sc.next();
                if (s.startsWith("'")) {
                    //consume until the next single-quote
                    s = s.substring(1);
                    while (sc.hasNext()) {
                        s += " " + sc.next();
                        if (s.endsWith("'")) {
                            s = s.substring(0, s.length() - 1);
                            break;
                        }
                    }
                }
                temp.add(s);
            }
            attrArr = temp.toArray(new String[temp.size()]);
        }
    }

    /**
     * I'm pretty sure this is supposed to exist in default Java under StringUtils
     * but for some reason it's not on my laptop...?
     * Finds the nth occurrence of a target substring in the this.input string
     * @param target A substring
     * @param n the number of the occurrence you want
     * @return Returns the index of the occurrence
     */
    private int ordinalIndexOf(String target, int n) {
        int idx = input.indexOf(target);
        if (idx == -1) {
            return -1;
        }
        for (int i = 1; i < n; i++) {
            idx = input.indexOf(target, idx + 1);
        }
        return idx;
    }
}