/*
 * Created by: Andrew Nguyen
 * Date: 2019-05-24
 * Time: 14:32
 * csci-330-surly
 */

/*
 *Edited by Jonathan McCamey
 *Date: 2019-06-01
 *csci-330-surly
 *Time 14:34
 *
 */
import java.util.*;
import java.lang.*;




public class ProjectParser {

  private String input;
  private String[] attributeNames;
  private Relation temp;

  public ProjectParser(String command, Relation table){
    input = command;
    temp = table;
  }

  private Relation Project(){
    ParseAttributeNames();
    LinkedList<Attribute> AttributeList = temp.getSchema()
    for(AttributeValue colName: AttributeList ){
      for(String attributeName: attributeNames){
        if(name.getName().equals(attributeName)){
          AttributeList.remove(colName);
          break;
        }
      }
    }
    LinkedList<Tuple> tempTuples = temp.getTuples();
    for(Tuple tuple: tempTuples){
      for(AttributeValue value: tuple){
        for(String attributeName: attributeNames){
          if(value.getName().equals(attributeName)){
            tuple.remove(value);
            break;
          }
        }
      }
    }
    return temp;

  }

  private void ParseRelationNames(){
    if (attributeNames == null) {
            attributeNames = input.substring(input.indexOf(" ") + 1).split(", ");
    }
  }






}
