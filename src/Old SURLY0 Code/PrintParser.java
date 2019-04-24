
/*
 * Created by: Andrew Nguyen
 * Date: 2019-04-10
 * Time: 12:36
 * SURLY0
 */

/**
 * Handles PRINT commands
 */
public class PrintParser {

//    #PRINTING TUPLES
//    PRINT COURSE, OFFERING, PREREQ;

    private String input;
    private String[] relationNames;

    public PrintParser(String input) {
        this.input = input;

    }

    public String[] parseRelationNames() {
        if (relationNames == null) {
            relationNames = input.substring(input.indexOf(" ") + 1).split(", ");
        }
        return relationNames;
    }

}