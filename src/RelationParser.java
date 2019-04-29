import java.util.Stack;

public class RelationParser {

//    #RELATION
//    #DEFINITIONS
//    RELATION COURSE (CNUM CHAR 8, TITLE CHAR 30, CREDITS NUM 4);

    private String input;
    private String name;    //name of the Relation, eg: COURSE
    private String[] attr;  //[attributeName dataType num, ...]
    private int attrCount;

    /**
     * Constructor for RelationParser
     * @param input raw String from text file, passed in by LexicalAnalyzer
     */
    public RelationParser(String input) {
        this.input = input;
        name = null;
        attr = null;
        attrCount = -1;
    }

    /**
     * Extracts and saves into the object the Relation name if it hasn't been done yet
     * @return Returns the name otherwise
     */
    public String parseRelationName() {
        if (name == null) {
            int firstSpace = ordinalIndexOf(" ", 1) + 1;
            int secondSpace = ordinalIndexOf(" ", 2);
            name = input.substring(firstSpace, secondSpace);
        }
        return name;
    }

    /**
     * Determines how many attributes an Item of the given Relation type must have
     * Also calls checkParenthesis to determine if the string is parenthesized properly
     * @return number of attributes
     */
    public int parseAttributeCount() {
        int firstParen = input.indexOf("(");
        String sub = input.substring(firstParen);
        if (checkParenthesis(sub)) {
            attr = sub.replace("(","").replace(")","").split(",");
            // Assure that attributes are evenly spaced, eg: "CNUM CHAR 8" and not "CNUM     CHAR   8"
            normalizeSpacing();
            attrCount = attr.length;
        } else {
            //parentheses not proper
            //Should ignore this line of input
            return -1;
        }
        return attrCount;
    }

    private void normalizeSpacing() {
        for (int i = 0; i < attr.length; i++) {
            attr[i] = attr[i].replaceAll("\\s+"," ").trim();    //String.replaceAll() regex
        }
    }

    public String[] getAttrFormat() {
        return attr;
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
        for (int i = 1; i < n; i++) {
            idx = input.indexOf(target, idx + 1);
        }
        return idx;
    }

    /**
     * Checks for proper parenthesization
     * @param substr substring
     * @return true/false
     */
    private static boolean checkParenthesis(String substr) {
        Stack<String> st = new Stack();
        for (Character c : substr.toCharArray()) {
            if (c.toString().equals("(")) {
                st.push(c.toString());
            }
            if (c.toString().equals(")")) {
                if (st.empty() || !st.pop().equals("(")) {
                    return false;
                }
            }
        }
        return true;
    }
}