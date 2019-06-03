
/*
 * Created by: Andrew Nguyen
 * Date: 2019-05-24
 * Time: 14:35
 * csci-330-surly
 */

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.tools.internal.ws.wsdl.document.soap.SOAPUse;

import java.util.ArrayList;
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
        } else if (clause.size() > 3) {
            //this means we have something like [CAMPUSADDR, =, CF 479, AND, EXTENSION, =, 3769]
            //How do we handle? Find the center most AND, OR boolean operator and divide it into left halves and right halves
            //ie: [CAMPUSADDR, =, CF 479, AND, EXTENSION, =, 3769]
            // --> extractTuples([CAMPUSADDR, =, CF 479) AND extractTuples([EXTENSION, =, 3769])

            if (clause.contains("AND")) {
                int firstAnd = clause.indexOf("AND");
                List<String> leftClause = clause.subList(0, firstAnd);
                List<String> rightClause = clause.subList(firstAnd + 1, clause.size());
                BooleanConditionHandler left = new BooleanConditionHandler(leftClause, relation);
                BooleanConditionHandler right = new BooleanConditionHandler(rightClause, relation);
                left.extractTuples();
                right.extractTuples();
                /**
                 * This works because we are passing in the same reference to relation to
                 * both the left and right halves. the AND operation means we want the left
                 * subclause and the right subclause to be true, so it's ok to use the
                 * same reference and apply the extraction of single conditions, for each condition
                 */
                return relation;
            }

            if (clause.contains("OR")) {
                int firstOr = clause.indexOf("OR");
                List<String> leftClause = clause.subList(0, firstOr);
                List<String> rightClause = clause.subList(firstOr + 1, clause.size());
                BooleanConditionHandler left = new BooleanConditionHandler(leftClause, relation.copy());
                BooleanConditionHandler right = new BooleanConditionHandler(rightClause, relation.copy());
                Relation lr = left.extractTuples();
                Relation rr = right.extractTuples();
                /**
                 * Unlike AND where we perform both the left and right subclause conditions on
                 * the same relation (and thus can use the same reference / object), for OR, we
                 * have to take the Union of the left and right subclauses.
                 * So we create a copy with relation.copy(), and then get the left and right halves.
                 * And then return the union of the tuples. The schema (LinkedList<Attribute>) is the same
                 */
                LinkedList<Tuple> leftTuples = lr.getTuples();
                leftTuples.addAll(rr.getTuples());
                return lr;
            }
            return relation;
        } else {
            return relation;
        }

        LinkedList<Tuple> origTuples = relation.getTuples();
        LinkedList<Tuple> newTuples = new LinkedList<>();
        for (Tuple t : origTuples) {
            switch (operator) {
                case "=":
                    if (t.getValue(attribute).equalsIgnoreCase(restriction)) {
                        newTuples.add(t);
                    }
                    break;
                case "!=":
                    if (!t.getValue(attribute).equalsIgnoreCase(restriction)) {
                        newTuples.add(t);
                    }
                    break;
                case "<":
                    if (Integer.parseInt(t.getValue(attribute)) < Integer.parseInt(restriction)) {
                        newTuples.add(t);
                    }
                    break;
                case ">":
                    if (Integer.parseInt(t.getValue(attribute)) > Integer.parseInt(restriction)) {
                        newTuples.add(t);
                    }
                    break;
                case "<=":
                    if (Integer.parseInt(t.getValue(attribute)) <= Integer.parseInt(restriction)) {
                        newTuples.add(t);
                    }
                    break;
                case ">=":
                    if (Integer.parseInt(t.getValue(attribute)) >= Integer.parseInt(restriction)) {
                        newTuples.add(t);
                    }
                    break;
            }
        }
        relation.setTuples(newTuples);
//        System.out.println(relation.toString());
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