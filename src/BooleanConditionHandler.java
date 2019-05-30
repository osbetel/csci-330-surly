
/*
 * Created by: Andrew Nguyen
 * Date: 2019-05-24
 * Time: 14:35
 * csci-330-surly
 */

public class BooleanConditionHandler {

    private String[] clause;
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
    public BooleanConditionHandler(String[] clause, Relation relation) {
        this.clause = clause;
        this.relation = relation;
    }

}