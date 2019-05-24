import java.util.*;
import java.lang.*;

/**
 * SurlyDatabase is meant to be the top-level object that holds all other objects and data
 * relating to the database.
 * LexicalAnalyzer has an instance of SurlyDatabase
 */
public class SurlyDatabase {

    private LinkedList<Relation> relations;

    /**
     * Constructor.
     */
    public SurlyDatabase() {
        relations = new LinkedList<>();
    }

    /**
     * Returns a Relation object if it is contained in the SurlyDatabase's LinkedList.
     * @param name The name of the Relation class you want to find/.
     * @return Relation obj. Should return null if Relation does not exist. //todo
     */
    public Relation getRelation(String name) {
        return relationBinarySearch(name, 0, relations.size());
    }

    /**
     * Because LinkedLists do not have a search function by object fields. ie: it uses
     * LinkedList.indexOf(Object o), but if you do not have an instance of that object,
     * you cannot search for it. Additionally, you can't search by object field.
     * For example, LinkedList.indexOf(o.getName()) is invalid.
     *
     * Since this is a relatively small project, it's easier to just write a custom binary
     * search than to override the Object.equals() method or write an extended LinkedList class.
     * @param name The name of the Relation we want
     * @param start 0
     * @param end size of the LinkedList - 1
     * @return Returns the desired Relation object. Save that reference.
     */
    private Relation relationBinarySearch(String name, int start, int end) {

        if (start == end) {
            return null;
        }

        int mid = (start + end) / 2;
        if (relations.get(mid).getName().equals(name)) {
            return relations.get(mid);
        } else if (start < mid && mid < end) {
            Relation left = relationBinarySearch(name, start, mid);
            Relation right = relationBinarySearch(name, mid, end);
            if (left != null) {
                return left;
            } else {
                return right;
            }
        }
        return null;
    }

    /**
     * Utilizes the binary search above to find a given Relation by name.
     * @param relName Name of the target Relation
     * @return True if Relation in Database, False otherwise
     */
    public boolean containsRelation(String relName) {
        Relation r = relationBinarySearch(relName, 0, relations.size());
        if (r == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Executes order 66.
     * Note, destroying removes all the data under a Relation, and removes it from the SurlyDatabase
     * @param name Name of the Relation to be destroyed.
     */
    public void destroyRelation(String name) {
        Relation target = getRelation(name);
        relations.remove(target);
    }

    /**
     * Creates a new Relation (categories) that you can then insert tuples under, so long as they
     * Follow that relation's format (which is in the LinkedList<Attribute> attached to a Relation obj.
     * @param relation The Relation object you want to insert into the SurlyDatabase.
     */
    public void createRelation(Relation relation) {
        relations.add(relation);
    }

    public void printRelation(String relName) {
        System.out.println(getRelation(relName).toString());
    }

    public void printCatalog() {
        String output = "";
        int totalPad = 0;
        output += String.format("%15s %15s", "RELATION", "ATTRIBUTE");
        totalPad += 15 * 2 + 1;
        output += "\n";

        output = "\n" + output;
        for (int i = 0; i < totalPad; i++) {
            output = "-" + output;
        }

        output = String.format("%15s", "CATALOG") + "\n" + output;

        for (int i = 0; i < totalPad; i++) {
            output += "-";
        }

        output += "\n";

        for (Relation r : relations) {
            output += String.format("%15s %15s", r.getName(), r.getAttrCount()) + "\n";
        }

        for (int i = 0; i < totalPad; i++) {
            output += "-";
        }

        output += "\n";
        System.out.println(output);
    }

}
