
/*
 * Created by: Andrew Nguyen
 * Date: 2019-05-24
 * Time: 14:32
 * csci-330-surly
 */

public class ProjectParser {

    private String input;
    private String[] relationNames;

    public ProjectParser(String input) {
        this.input = input;
    }

    public String[] parseRelationNames() {
        if (relationNames == null) {
            relationNames = input.substring(input.indexOf(" ") + 1).split(", ");
        }
        return relationNames;
    }

}