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