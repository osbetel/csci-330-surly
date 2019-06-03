/*
 * Created by: Andrew Nguyen
 * Date: 2019-05-24
 * Time: 14:32
 * csci-330-surly
 */

/*
 *Edited by Jonathan McCamey
 *Date: 2019-06-01
 *csci-330-surly
 *Time 14:57
 */
import java.util.*;
import java.lang.*;

public class ProjectParser {

    private String input;

    public ProjectParser(String input) {
        this.input = input;
    }

    public Relation project(Relation copy) {
        // Supposing the input = "PROJECT CNUM, CREDITS FROM COURSE"
        List<String> desiredAttr = parseProjectStatement();
        //now we have the deep copy of the relation, and the list of desired attributes to project
        if (desiredAttr.size() == 1) { return copy; }
        Relation newRel = extractAttributes(copy, desiredAttr);
        return newRel;
    }

    public Relation extractAttributes(Relation copy, List<String> lst) {
        // from PROJECT command. In this case clause is <attribute names>
        //temprelationname = PROJECT <attributenames> FROM relationname;
        LinkedList<Attribute> schema = copy.getSchema();
        LinkedList<Tuple> tuples = copy.getTuples();
        LinkedList<Attribute> newSchema = new LinkedList<>();
        LinkedList<Tuple> newTuples = new LinkedList<>();

        for (Attribute attr : schema) {
            if (clauseContains(attr.getName(), lst)) {
                //if the attribute is not mentioned in the clause, remove it
                //need to also remove all corresponding AttributeValues per tuple.
                newSchema.add(attr);
            }
        }

        for (Tuple t : tuples) {
            // for each tuple, we need to access its AttributeValue LinkedList
            // And for each AttributeValue in the list, if we removed it from the
            // schema, then remove the corresponding AttributeValue
            LinkedList<AttributeValue> attrValueList = t.getValueList();
            LinkedList<AttributeValue> newAttrList = new LinkedList<>();
            for (AttributeValue av : attrValueList) {
                // if av has no parent Attribute type, delete
                if (schema.contains(av.getParentType())) {
                    newAttrList.add(av);
                }
            }
            newTuples.add(new Tuple(newAttrList));
        }
        copy.setSchema(newSchema);
        copy.setTuples(newTuples);
        return copy;
    }

    private boolean clauseContains(String attrName, List<String> clause) {
        for (String s : clause) {
            if (attrName.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private List<String> parseProjectStatement() {
        ArrayList<String> separated = BooleanConditionHandler.combineSingleQuotes(input.split(" "));
        if (!separated.contains("FROM")) {
            return separated.subList(1, separated.size());
        }
        return removeCommas(separated.subList(separated.indexOf("PROJECT") + 1, separated.indexOf("FROM")));
    }

    public String parseRelationName() {
        String[] line = input.split(" ");
        return line[line.length - 1];
    }

    private List<String> removeCommas(List<String> lst) {
        for (int i = 0; i < lst.size(); i++) {
            if (lst.get(i).endsWith(",")) {
                lst.set(i, lst.get(i).substring(0, lst.get(i).length() - 1));
            }
        }
        return lst;
    }






}
