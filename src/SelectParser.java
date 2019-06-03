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

            //if tuple doesn't meet boolean conditions, remove it from list...
            //need to dynamically determine a list of boolean conditions that
            //some tuple needs to meet, otherwise it is thrown out.
            //issues: size of boolean clause can change from a simple x = y, to (x and y) = (a or b), etc. How to handle?
            List<String> clause = parseBooleanClause();
            BooleanConditionHandler bch = new BooleanConditionHandler(clause, copy);
            Relation newRel = bch.extractTuples();
//            System.out.println(newRel.toString());
            return newRel;
        } else {
            return copy;
        }
    }

    private List<String> parseBooleanClause() {
        ArrayList<String> separated = combineSingleQuotes(input.split(" "));
        /**
         * eg: "student.ID = '12345'" --> ["student.ID", "=", "12345"]
         * eg: TITLE = ‘DATABASE SYSTEMS’ or CNUM != CSCI301 and CREDITS > 3
         * --> ["TITLE", "=", "DATABASE SYSTEMS", "or", "CNUM", "!=", "CSCI301", "and", "CREDITS", ">", "3"]
         * Don't forget boolean precedence! and > or
         */
        return separated.subList(separated.indexOf("WHERE") + 1, separated.size());
    }

    private ArrayList<String> combineSingleQuotes(String[] list) {

        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < list.length - 1; i++) {
            String left = list[i];
            String right = list[i + 1];
            if (result.size() == 0) {
                result.add(left);
            }
            if (left.startsWith("'") && right.endsWith("'")) {
                result.add(left.substring(1) + " " + right.substring(0, right.length() - 1));
            } else if (!right.startsWith("'")) {
                result.add(right);
            }
        }
        return result;

    }

    public String parseRelationName() {
        String[] line = input.split(" ");
        return line[1];
    }
}
