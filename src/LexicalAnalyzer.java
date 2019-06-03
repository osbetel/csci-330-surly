import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
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
    private SurlyDatabase primaryDB;
    private SurlyDatabase tempDB;

    /**
     * Constructor.
     */
    public LexicalAnalyzer() {
        primaryDB = new SurlyDatabase();
        tempDB = new SurlyDatabase();
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
            String nextString = sc.next();  //throws error if there is not an extra line of whitespace at the end of the test.txt file
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
                int attrCount = rp.parseAttributeCount(); //var not used but the function execution is necessary
                String[] attrFormat = rp.getAttrFormat();

                if (!primaryDB.containsRelation(relationName)) {
                    LinkedList<Attribute> attrList = new LinkedList<>();
                    for (String s : attrFormat) {
                        //format of s is "CNUM CHAR 8" and attrFormat is [CNUM CHAR 8, TITLE CHAR 30, CREDITS NUM 4]
                        String[] arr = s.split(" ");
                        Attribute attribute = new Attribute(arr[0], arr[1], Integer.parseInt(arr[2]));
                        attrList.add(attribute);
                    }
                    primaryDB.addRelation(new Relation(relationName, attrList, true));
                }
            }

            else if (commandToParse.startsWith("INSERT")) {
                // INSERT relationName [relation attributes]
                InsertParser ip = new InsertParser(commandToParse);
                String relationType = ip.parseRelationName();
                int itemAttrCount = ip.parseAttributeCount();

                //inserts the Item into database under the relationType object
                if (primaryDB.containsRelation(relationType)) {
                    Relation rel = primaryDB.getRelation(relationType);
                    if (rel.getAttrCount() == itemAttrCount) {
                        rel.insert(ip.generateTuple(rel.getSchema()));
                    } else {
                        System.out.println("Incorrect num of attributes: \"" + commandToParse + "\"");
                    }
                }
            }

            else if (commandToParse.startsWith("DELETE")) {
                DeleteParser dp = new DeleteParser(commandToParse);
                String relName = dp.parseRelationName();

                if (primaryDB.containsRelation(relName)) {
                    Relation rel = primaryDB.getRelation(relName);
                    dp.delete(rel);
                } else {
                    System.out.println("Relation \"" + relName + "\" is not in the database.");
                }
            }

            else if (commandToParse.startsWith("DESTROY")) {
                DestroyParser dp = new DestroyParser(commandToParse);
                String relName = dp.parseRelationName();

                if (primaryDB.containsRelation(relName)) {
                    primaryDB.destroyRelation(relName);
                } else {
                    System.out.println("Relation \"" + relName + "\" is not in the database.");
                }
            }

            else if (commandToParse.startsWith("PRINT")) {
                // PRINT relationName1, relationName2, ...
                // Should just print all the values in that relation
                PrintParser pp = new PrintParser(commandToParse);
                for (String r : pp.parseRelationNames()) {
                    if (r.equals("CATALOG")) {
                        primaryDB.printCatalog();
                    } else {
                        System.out.println(fetchRelation(r));
                    }
                }
            }

            else if (commandToParse.contains("JOIN")) {
                JoinParser jp = new JoinParser(commandToParse);
                List<String> relNames = jp.parseRelationNames();
                Relation A = fetchRelation(relNames.get(0)).copy();
                Relation B = fetchRelation(relNames.get(1)).copy();
                Relation newRel = jp.join(A, B);
                newRel.setName(jp.parseAssignmentName());
                tempDB.addRelation(newRel);
                System.out.println(newRel);
            }

            //Project parser constructor takes in a string that conatains which atributes to project
            //and a deep copy provided by rel.copy, which can be edited to have the desired
            //atrributes to print
            else if (commandToParse.contains("PROJECT")) {
                ProjectParser pp = new ProjectParser(commandToParse);
                Relation rel = fetchRelation(pp.parseRelationName());
                Relation newRel = pp.project(rel.copy());
                newRel.setName(pp.parseAssignmentName());
                tempDB.addRelation(newRel);
            }

            else if (commandToParse.contains("SELECT")) {
                SelectParser sp = new SelectParser(commandToParse);
                Relation rel = fetchRelation(sp.parseRelationName());
                Relation newRel = sp.select(rel.copy());
                newRel.setName(sp.parseAssignmentName());
                tempDB.addRelation(newRel);
            }

            else {  //For random or unrecognized, non-legitimate commands, just skips over them
                continue;
            }
        }
//        for (Relation r : tempDB.allRelations()) {
//            System.out.println(r);
//        }
    }

    /**
     * Checks both temp and primary DB for the Relation.
     * To be used in cases like SELECT, PROJECT, JOIN
     * But if not base / primary Relations, can't be used for INSERT, DELETE, DESTROY
     * @param name
     * @return
     */
    public Relation fetchRelation(String name) {
        if (primaryDB.containsRelation(name)) {
            return primaryDB.getRelation(name);
        } else if (tempDB.containsRelation(name)) {
            return tempDB.getRelation(name);
        } else {
            System.out.println("Relation \"" + name + "\" is not in the database.");
            return null;
        }
    }

    public String scrubWhitespace(String s) {
        return s.trim().replaceAll(" +", " ");
    }

}