
/*
 * Created by: Andrew Nguyen
 * Date: 2019-04-10
 * Time: 12:36
 * SURLY0
 */

import java.util.ArrayList;
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
    private String name;            //Item name: CSCI145, GREGORY, etc.
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

    public String parseItemName() {
        if (name == null) {
            //Category it belongs in, like COURSE, DEPT, PREREQ, etc.
            name = input.substring(ordinalIndexOf(" ", 2) + 1, ordinalIndexOf(" ", 3)); //Title/name, CSCI141, GREGORY, etc.
        }
        return name;
    }

    public int parseAttributeCount() {
        String attr = input.substring(ordinalIndexOf(" ", 2) + 1);
        attributesToArray(attr);
        return attrArr.length;
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
     * Creates and returns an Item object from the line of input.
     * Called in LexicalAnalyzer to add an Item to the database under the proper Relation
     * @return Item object.
     */
    public Item generateItem() {
        if (name == null) {
            parseItemName();
        }
        Item i = new Item(name, attrArr);
        return i;
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