import java.util.*;
import java.lang.*;

/**
 * SurlyDatabase is meant to be the top-level object that holds all other objects and data
 * relating to the database.
 * LexicalAnalyzer has an instance of SurlyDatabase
 */
public class SurlyDatabase{

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
        return relationBinarySearch(name, 0, relations.size() - 1);
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

        int mid = (start + end) / 2;
        if (relations.get(mid).getName().equals(name)) {
            return relations.get(mid);
        } else if (start < mid && mid < end) {
            Relation left = relationBinarySearch(name, start, mid);
            Relation right = relationBinarySearch(name, mid, end);
            //todo: need to handle case where Relation doesn't exist
            // or case where multiple copies of the relation exist with
            // identical names but different attributes.
            if (left != null) {
                return left;
            } else {
                return right;
            }
        }
        return null;
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
     * The first galactic empire.
     * @param relation The Relation object you want to insert into the SurlyDatabase.
     */
    public void createRelation(Relation relation) {
        relations.add(relation);
    }

}
