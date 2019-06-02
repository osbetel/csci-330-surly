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
 *Time 14:57
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
    for(AttributeValue colName: AttributeList){
      boolean wantedAtt = false;
      for(String attributeName: attributeNames){
        if(colName.getName().equals(attributeName)){
          wantedAtt = true;
        }
      }
      if(!wantedAtt){
        AttributeList.remove(colName);
      }
    }
    LinkedList<Tuple> tempTuples = temp.getTuples();
    for(Tuple tuple: tempTuples){
      LinkedList<AttributeValue> tempAttrVal= tuple.getValue();
      for(AttributeValue value: tempAttrVal){
        boolean wantedVal = false;
        for(String attributeName: attributeNames){
          if(value.getName().equals(attributeName)){
            wantedVal = true;
          }
        }
        if(!wantedVal){
          tempAttrVal.remove(value);
        }
      }
    }
    return temp;

  }

  private void ParseAttributeNames(){
    if (attributeNames == null) {
            attributeNames = input.substring(input.indexOf(" ") + 1).split(", ");
    }
  }






}
