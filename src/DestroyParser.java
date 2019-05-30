import java.lang.*;

/**
 * Parses DESTROY statements
 */
public class DestroyParser {

  String input;

  public DestroyParser(String input){
    this.input = input;
  }

  public String parseRelationName() {
    String[] line = input.split(" ");
    return line[1];
  }
}
