/*
 * Created by: Andrew Nguyen
 * Date: 2019-04-10
 * Time: 12:36
 * SURLY0
 */

public class Main {

    public static void main(String[] args) {
        executeOrder66(args);
    }

    public static void executeOrder66(String[] args) {
        LexicalAnalyzer surly = new LexicalAnalyzer();
        surly.run(args[0]); //Asuuming args[0] is the text file containing input
        return;
    }

}
