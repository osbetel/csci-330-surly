

public class main {

  public static void main (String[] args) {
    LexicalAnalyzer lexi = new LexicalAnalyzer();
    lexi.run(args[0]);
//    test();
  }

  private static void test() {
    LexicalAnalyzer lexi = new LexicalAnalyzer();
    lexi.run("test.txt");
  }
}
