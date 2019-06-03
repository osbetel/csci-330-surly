import java.lang.*;
import java.util.LinkedList;

/**
 * Parses DELETE statements
 */
public class DeleteParser {

    String input;

    public DeleteParser(String input) {
        this.input = input;
    }

    public Relation delete(Relation rel) {
        // We can think of DELETE as an operation of two sets.
        // Let set A = rel
        // and set B = SELECT <relation> WHERE <boolean condition>
        //Then DELETE <relation> WHERE <boolean condition> --> results in A - B or A intersect B'
        input = input.replace("DELETE", "SELECT");
        SelectParser sp = new SelectParser(input);
        Relation B = sp.select(rel.copy());

        LinkedList<Tuple> aTuple = rel.getTuples();
        LinkedList<Tuple> bTuple = B.getTuples();
        LinkedList<Tuple> newTuple = new LinkedList<>(aTuple);

        for (Tuple bt : bTuple) {
            for (Tuple at : aTuple) {
                if (at.toString().equals(bt.toString())) {
                    newTuple.remove(at);
                }
            }
        }

        rel.setTuples(newTuple);
        return rel;
    }


    public String parseRelationName() {
        String[] line=input.split(" ");
        return line[1];
    }
}
