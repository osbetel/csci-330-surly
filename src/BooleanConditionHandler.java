
/*
 * Created by: Andrew Nguyen
 * Date: 2019-05-24
 * Time: 14:35
 * csci-330-surly
 */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BooleanConditionHandler {

    private List<String> clause;
    private Relation relation;


    /**
     * Purpose of this class is to handle boolean conditions that come
     * after a WHERE clause.
     * For example, if using DELETE <relation-name> WHERE <boolean conditions>
     * This class is called upon encountering a WHERE clause, and handles everything
     * concerning those boolean conditions.
     */

    /**
     * Constructor
     * @param clause the string of everything after the WHERE clause.
     *               eg: if we have "WHERE student.ID = '12345'" then we would use
     *               "student.ID = '12345'" as input to the constructor.
     * @param relation The relation object corresponding to the <relation-name>
     *                 That the DELETE, SELECT, etc. command is being called on.
     */
    public BooleanConditionHandler(List<String> clause, Relation relation) {
        this.clause = clause;
        this.relation = relation;
    }

    public Relation extractTuples() {
        //temprelationname = SELECT relationname WHERE <conditions>;
        //clause = ["CNUM", "=", "12345"]; or something similar
        String attribute, operator, restriction;
        if (clause.size() == 3) {
            attribute = clause.get(0);
            operator = clause.get(1);
            restriction = clause.get(2);
        } else {
            return relation;
        } //todo need to make this recursive for multi condition handling

        LinkedList<Tuple> tuples = relation.getTuples();
        for (Tuple t : tuples) {
            switch (operator) {
                case "=":
                    if (!t.getValue(attribute).equals(restriction)) {
                        tuples.remove(t);
                    }
                    break;
                case "!=":
                    if (!!t.getValue(attribute).equals(restriction)) {
                        tuples.remove(t);
                    }
                    break;
                case "<":
                    if (!(Integer.parseInt(t.getValue(attribute)) < Integer.parseInt(restriction))) {
                        tuples.remove(t);
                    }
                    break;
                case ">":
                    if (!(Integer.parseInt(t.getValue(attribute)) > Integer.parseInt(restriction))) {
                        tuples.remove(t);
                    }
                    break;
                case "<=":
                    if (!(Integer.parseInt(t.getValue(attribute)) <= Integer.parseInt(restriction))) {
                        tuples.remove(t);
                    }
                    break;
                case ">=":
                    if (!(Integer.parseInt(t.getValue(attribute)) >= Integer.parseInt(restriction))) {
                        tuples.remove(t);
                    }
                    break;
            }
        }
        return relation;
    }

    public Relation extractAttributes() {
        // from PROJECT command. In this case clause is <attribute names>
        //temprelationname = PROJECT <attributenames> FROM relationname;
        LinkedList<Attribute> schema = relation.getSchema();
        LinkedList<Tuple> tuples = relation.getTuples();
        for (Attribute attr : schema) {
            if (!clauseContains(attr.getName())) {
                //if the attribute is not mentioned in the clause, remove it
                //need to also remove all corresponding AttributeValues per tuple.
                schema.remove(attr);
            }
        }

        for (Tuple t : tuples) {
            // for each tuple, we need to access its AttributeValue LinkedList
            // And for each AttributeValue in the list, if we removed it from the
            // schema, then remove the corresponding AttributeValue
            LinkedList<AttributeValue> attrValueList = t.getValueList();
            for (AttributeValue av : attrValueList) {
                // if av has no parent Attribute type, delete
                if (!schema.contains(av.getParentType())) {
                    attrValueList.remove(av);
                }
            }
        }
        return relation;
    }

    private boolean clauseContains(String attrName) {
        for (String s : clause) {
            if (attrName.equals(s)) {
                return true;
            }
        }
        return false;
    }
}