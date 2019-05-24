import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Reads the input file and creates + stores a hashmap to function as the database
 * RELATION, INSERT, and PRINT commands are handled by their respective parsers
 */
public class LexicalAnalyzer {

    // top level hashmap has all the relation names as keys,
    // ie: COURSE, PREREQ, OFFERING, etc. Using database.get("COURSE")
    // would return the Relation object for COURSE, which contains all the items that fall
    // under the COURSE Relation.
    private SurlyDatabase database;

    /**
     * Constructor.
     */
    public LexicalAnalyzer() {
        this.database = new SurlyDatabase();
    }

    public void run(String filename) {
        //do something with file
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        /**
         * Some general rules for parsing file input:
         * 1) if a # symbol comes up then disregard everything after and skip to the next line
         * 2) There's 3 commands, RELATION, INSERT, and PRINT, and they're handled
         *    by calling their respective parsers
         * 3) If the line is fully blank, skip it
         * 4) When we run into a command keyword, we consume until the first instance of a semi-colon ;
         */

        sc.useDelimiter(";"); //each sc.next consumes the text file up to the next semi-colon
        while (sc.hasNextLine()) {
            //todo: add some handling for bad input
            String nextString = sc.next();
            String commandToParse = "";
            Scanner sc2 = new Scanner(nextString);
            while (sc2.hasNextLine()) {
                String s = sc2.nextLine();
                if (!s.startsWith("#")) {
                    commandToParse += s.trim();
                }
            }

            //Scrub input for extra whitespaces first
            commandToParse = scrubWhitespace(commandToParse);

            if (commandToParse.startsWith("RELATION")) {
                //Format is as follows: RELATION relationName (attrName1 <dataType> numOfChars,...);
                RelationParser rp = new RelationParser(commandToParse);
                String relationName = rp.parseRelationName();
                int attrCount = rp.parseAttributeCount();
                String[] attrFormat = rp.getAttrFormat();

                if (!database.containsRelation(relationName)) {
                    LinkedList<Attribute> attrList = new LinkedList<>();
                    for (String s : attrFormat) {
                        //format of s is "CNUM CHAR 8" and attrFormat is [CNUM CHAR 8, TITLE CHAR 30, CREDITS NUM 4]
                        String[] arr = s.split(" ");
                        Attribute attribute = new Attribute(arr[0], arr[1], Integer.parseInt(arr[2]));
                        attrList.add(attribute);
                    }
                    database.createRelation(new Relation(relationName, attrList));
                }
            } else if (commandToParse.startsWith("INSERT")) {
                // INSERT relationName [relation attributes]
                InsertParser ip = new InsertParser(commandToParse);
                String relationType = ip.parseRelationName();
                int itemAttrCount = ip.parseAttributeCount();

                //inserts the Item into database under the relationType object
                if (database.containsRelation(relationType)) {
                    Relation rel = database.getRelation(relationType);
                    if (rel.getAttrCount() == itemAttrCount) {
                        rel.insert(ip.generateTuple(rel.getSchema()));
                    } else {
                        System.out.println("Incorrect num of attributes: \"" + commandToParse + "\"");
                    }
                }
            } else if (commandToParse.startsWith("DELETE")) {
                DeleteParser dp = new DeleteParser(commandToParse);
                String relName = dp.parseRelationName();

                if (database.containsRelation(relName)) {
                    Relation rel = database.getRelation(relName);
                    rel.delete();
                } else {
                    System.out.println("Relation \"" + relName + "\" is not in the database.");
                }
            } else if (commandToParse.startsWith("DESTROY")) {
                DestroyParser dp = new DestroyParser(commandToParse);
                String relName = dp.parseRelationName();

                if (database.containsRelation(relName)) {
                    database.destroyRelation(relName);
                } else {
                    System.out.println("Relation \"" + relName + "\" is not in the database.");
                }
            } else if (commandToParse.startsWith("PRINT")) {
                // PRINT relationName1, relationName2, ...
                // Should just print all the values in that relation
                PrintParser pp = new PrintParser(commandToParse);
                for (String r : pp.parseRelationNames()) {
                    if (r.equals("CATALOG")) {
                        database.printCatalog();
                    } else {
                        database.printRelation(r);
                    }
                }
                //For testing
//                System.out.println("\n\n\n\n\n\n\n\n");
//                database.getRelation("COURSE").delete();
//                database.printRelations();
            } else if (commandToParse.startsWith("JOIN")) {
                //todo fill out body
            } else if (commandToParse.startsWith("PROJECT")) {
                //todo fill out body
            } else if (commandToParse.startsWith("SELECT")) {
                //todo fill out body
            } else {  //For random or unrecognized, non-legitimate commands, just skips over them
                continue;
            }
        }
    }

    public String scrubWhitespace(String s) {
        return s.trim().replaceAll(" +", " ");
    }

}