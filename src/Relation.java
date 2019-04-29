import org.w3c.dom.Attr;

import java.util.*;
import java.lang.*;

public class Relation{

    private String name;
    private LinkedList<Attribute> schema;
    private LinkedList<Tuple> tuples;

    public Relation(String name, LinkedList<Attribute> schema){
        this.name = name;
        this.schema = schema;
        this.tuples = new LinkedList<>();
    }

    public void print() {
        System.out.println("RELATION ATTRIBUTE FORMAT: " + schema);
        System.out.println("ITEMS: " + tuples);
    }

    public void insert(Tuple tuple) {
        //Inserting an item into a relation class. eg: if we have tuple
        //('Bob', 19254, 'Comp. Sci.') under a relation "table" of format
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
