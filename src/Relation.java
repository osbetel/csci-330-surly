import java.util.*;
import java.lang.*;

public class Relation{

    private String name;
    private LinkedList<Attribute> schema;
    private LinkedList<Tuple> tuples;

    public Relation(String name, LinkedList schema){
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
}
