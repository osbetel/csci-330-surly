import java.util.*;
import java.io.*;
import java.lang.*;

public class RelationParser{

  String input;

  RelationParser(String line){
    input= line;

  }

  public String parseRelationName(){
    String sub=input.substring(0,input.indexOf("("));
    String[] subArray= sub.split(" ");
    String relation=subArray[1];
    return relation;
  }

  public Relation parseRelation(){
    String subInput=input.substring(input.indexOf("(")+1,input.indexOf(")"));
    String[] AttributeArr=subInput.split(",");
    LinkedList<Attribute> attributes= new LinkedList<Attribute>();
    for(String attr: AttributeArr){
      String[] AttParam=attr.split(" ");
      AttParam=spaceCleaner(AttParam);
      Attribute attribute=new Atribute(AttParam[0], AttParam[1], AttParam[2]);
      attributes.add(attribute);
    }
    Relation relate= new Relation(parseRelation(), attributes, null);
    return relate;
  }
  public String[] spaceCleaner(String[] line){
    String[] cleaned=new String[3];
    int i=0;
    for(String string: line){
      if(string.equals("")==false || string.equals(" ")==false){
        cleaned[i]=string;
        i++;
      }
    }
    return cleaned;
  }

  public boolean syntaxCheck(String[] attributes){
    for(String attribute: attributes){
      String[] attriCheck=attribute.split(" ");
      int count =0;
      for(String word:attriCheck){
        if(word.equals("")!=true){
          count++;
        }
      }
      if(count!=3){
        return false;
      }
    }
    return true;
  }
}
