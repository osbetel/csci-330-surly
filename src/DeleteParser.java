import java.lang.*;

/**
 * Parses DELETE statements
 */
public class DeleteParser{

  String input;

  public DeleteParser(String input){
    this.input=input;
  }

  //todo add parsing for DELETE <relationname> WHERE <condition>

  public String parseRelationName() {
    String[] line=input.split(" ");
    return line[1];
  }
}
