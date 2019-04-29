import java.util.*;
import java.lang.*;

/**
 * Defines a Relation in the database. In terms of hierarchy, this is right under
 * the SurlyDatabase obj. A Relation must exist first in order for relevant tuples
 * to be inserted under it.
 */
public class Relation {

    private String name;
    private LinkedList<Attribute> schema;
    private LinkedList<Tuple> tuples;

    /**
     * Constructor
     * @param name Name of the Relation
     * @param schema A LinkedList defining the schema of tuples that will go under
     *               this Relation. eg: schema may have the form
     *               [Attr1, Attr2, Attr3], where each is an Attribute obj.
     *               And each Attribute object tells us what format and type
     *               the corresponding AttributeValue (int the Tuple) must take.
     */
    public Relation(String name, LinkedList<Attribute> schema){
        this.name = name;
        this.schema = schema;
        this.tuples = new LinkedList<>();
    }

    public void insert(Tuple tuple) {
        //Inserting an item into a relation class. eg: if we have tuple
        //('Bob', 19254, 'Comp. Sci.') under a relation of format
        //(student name, ID, major), called "StudentData," then we're
        //Inserting the tuple into the StudentData table.
        tuples.add(tuple);
    }

    public void delete() {
        //NOT "destroy," destroy removes relation from database entirely.
        //Delete simply erases all contents under that relation.
        schema = new LinkedList<>();
        tuples = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    //Specifically, returns how many attributes something in this Relation should have
    public int getAttrCount() {
        return schema.size();
    }

    @Override
    public String toString() {
        return  padRight("Relation Name:", 20) + name + "\n" +
                padRight("Format:", 20) + schema + "\n" +
                padRight("Current Entries:", 20) + tuples + "\n";
    }

    public final LinkedList<Attribute> getSchema() {
        return schema;
    }

    private static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }
}
