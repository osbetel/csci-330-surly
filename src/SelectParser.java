import java.util.*;
import java.lang.*;

public class SelectParser{
    private String input;

    //FORMAT: SELECT OFFERING WHERE CNUM = CSCI241 and SECTION > 27922;
    public SelectParser(String input) {
        this.input = input;
    }

    public Relation extract(Relation copy) {
        //Where copy is a deep copy of the relation named in the SELECT clause

        if (input.contains("WHERE")) {

            LinkedList<Tuple> values = copy.getTuples();
            for (Tuple tuple: values) {
                //if tuple doesn't meet boolean conditions, remove it from list...
                //need to dynamically determine a list of boolean conditions that
                //some tuple needs to meet, otherwise it is thrown out.
                //issues: size of boolean clause can change from a simple x = y, to (x and y) = (a or b), etc. How to handle?
                String[] clause = parseBooleanClause();
                BooleanConditionHandler bch = new BooleanConditionHandler(clause, copy);
            }
            return copy;
        } else {
            return copy;
        }
    }

    private String[] parseBooleanClause() {
        String[] separated = input.split(" ");
        /**
         * eg: "student.ID = '12345'" --> ["student.ID", "=", "12345"]
         * eg: TITLE = ‘DATABASE SYSTEMS’ or CNUM != CSCI301 and CREDITS > 3
         * --> ["TITLE", "=", "DATABASE SYSTEMS", "or", "CNUM", "!=", "CSCI301", "and", "CREDITS", ">", "3"]
         * Don't forget boolean precedence! and > or
         */

        return separated;
    }

    public String parseRelationName() {
        String[] line = input.split(" ");
        return line[1];
    }
}
