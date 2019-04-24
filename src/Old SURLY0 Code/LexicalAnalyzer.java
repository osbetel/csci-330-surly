/*
 * Created by: Andrew Nguyen
 * Date: 2019-04-10
 * Time: 12:36
 * SURLY0
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
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
    HashMap<String, Relation> database;

    /**
     * Constructor
     */
    public LexicalAnalyzer() {
        this.database = new HashMap<>();
    }

    public void run(String filename) {
        //do something with file
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
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

            String nextString = sc.next();
            String commandToParse = "";
            Scanner sc2 = new Scanner(nextString);
            while (sc2.hasNextLine()) {
                String s = sc2.nextLine();
                if (!s.startsWith("#")) {
                    commandToParse += s.trim();
                }
            }

            if (commandToParse.startsWith("RELATION")) {
                //Format is as follows: RELATION relationName (attrName1 <dataType> numOfChars,...);
                RelationParser rp = new RelationParser(commandToParse);
                String relationName = rp.parseRelationName();
                int attrCount = rp.parseAttributeCount();
                String[] attrFormat = rp.getAttrFormat();

                if (!database.containsKey(relationName)) {
                    database.put(relationName, new Relation(relationName, attrCount, attrFormat));
                }
            }

            else if (commandToParse.startsWith("INSERT")) {
                // INSERT relationName [relation attributes]
                InsertParser ip = new InsertParser(commandToParse);
                String relationType = ip.parseRelationName();
                int itemAttrCount = ip.parseAttributeCount();

                //inserts the Item into database under the relationType object
                if (database.containsKey(relationType)) {
                    if (database.get(relationType).getAttrCount() == itemAttrCount) {
                        database.get(relationType).addItem(ip.generateItem());
                    } else {
                        System.out.println("Incorrect num of attributes: \"" + commandToParse + "\"");
                    }
                }
            }

            else if (commandToParse.startsWith("PRINT")) {
                // PRINT relationName1, relationName2, ...
                // Should just print all the values in that relation
                PrintParser pp = new PrintParser(commandToParse);
                for (String r : pp.parseRelationNames()) {
                    printRelation(r);
                }
            }

            else {  //For random or unrecognized, non-legitimate commands, just skips over them
                continue;
            }
        }
    }

    private void printRelation(String relName) {
        Relation rel = database.get(relName);
        if (rel == null) {
            System.out.println("Relation \"" + relName + "\" does not exist.");
        } else {
            System.out.println(rel);
        }
    }
}