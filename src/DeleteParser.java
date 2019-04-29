import java.lang.*;

/**
 * Parses DELETE statements
 */
public class DeleteParser{

  String input;

  public DeleteParser(String input){
    this.input=input;
  }

  public String parseRelationName() {
    String[] line=input.split(" ");
    return line[1];
  }
}
