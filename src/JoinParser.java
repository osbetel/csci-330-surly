
/*
 * Created by: Andrew Nguyen
 * Date: 2019-05-24
 * Time: 14:31
 * csci-330-surly
 */

import java.util.ArrayList;
import java.util.List;

public class JoinParser {
    String input;
    List<String> statement;

    public JoinParser(String input) {
        this.input = input;
        statement = parseStatement();
    }

    public Relation join(Relation A, Relation B) {
        /**
         * Like the DeleteParser, we can think of join as a set operation.
         * With sets A, B, we want to get A intersect B on some condition
         * Instead of a full intersection, it's a partial intersection.
         * The way we're going to do this is to run SelectParser, interpreting A and B as one
         * giant set. When we have condition A.con1 = x AND B.con2 = A.con2, we can pull the
         * matching tuples from A and then B since they'll still be separate
         *
         * MAKE SURE A is the SMALLER RELATION THAN B
         * This way we can reduce handling of duplicates too much
         */
        //To add two relations together, we need to add onto the schema, and add on the tuples
        Relation C = A.copy();
        C.getSchema().addAll(B.getSchema());

        ArrayList<String> seenA = new ArrayList<>();
        ArrayList<String> seenB = new ArrayList<>();

        for (Attribute at : A.getSchema()) {
            if (!seenA.contains(at.getName())) {
                seenA.add(at.getName());
            }
        }

        for (Attribute bt : B.getSchema()) {
            if (seenA.contains(bt.getName())) {
                seenB.add(bt.getName());
            }
        }

        for (Tuple t : C.getTuples()) {
            boolean once = true;
            for (Tuple tb : B.getTuples()) {
                if (once) {
                    t.getValueList().addAll(tb.getValueList());
                    once = false;
                }
            }
        }
        return C;
    }


    /**
     * Parses the input string into an ArrayList like [JOIN, relA, relB, ON, conditions...]
     */
    public List<String> parseStatement() {
        ArrayList<String> separated = BooleanConditionHandler.combineSingleQuotes(input.split(" "));
        return BooleanConditionHandler.removeCommas(separated);
    }

    /**
     * Extracts from the statement, the sublist of all relation names
     */
    public List<String> parseRelationNames() {
        return statement.subList(3, statement.indexOf("ON"));
    }

    private List<String> parseBooleanClause() {
        ArrayList<String> separated = BooleanConditionHandler.combineSingleQuotes(input.split(" "));
        return separated.subList(separated.indexOf("ON") + 1, separated.size());
    }

    /**
     * Parses the name of the assignment, ie: J = JOIN ... will be stored in the tempDB as J.
     * @return
     */
    public String parseAssignmentName() {
        return input.split(" ")[0];
    }
}