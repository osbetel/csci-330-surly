import java.util.*;
import java.io.*;
import java.lang.*;

public class AttributeValue{
  String name;
  String value;

  public void AttributeValue(String name, String value){
    this.name = name;
    this.value = value;
  }
  public String getName(){
      return name;
  }

  public String value(){
    return value;
  }

}
