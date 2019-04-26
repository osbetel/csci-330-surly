import java.util.*;
import java.lang.*;

public class SurlyDatabase{

    private LinkedList<Relation> relations;

    public SurlyDatabase(){
        relations = new LinkedList<Relation>();
    }

    public Relation getRelation(String name) {
        return relationBinarySearch(name, 0, relations.size() - 1);
    }

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

    public void destroyRelation(String name) {
        Relation target = getRelation(name);
        relations.remove(target);
    }

    public void createRelation(Relation relation) {
        relations.add(relation);
    }

}
