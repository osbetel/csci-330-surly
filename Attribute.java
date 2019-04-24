import java.util.*;
import java.io.*;
import java.lang.*;

public class Attribute{

  String name;
  String dataType;
  int length;

  public Attribute(String name, String dataType, int length){
    this.name = name;
    this.dataType = name;
    this.length = length;
  }
  
  public String getName(){
      return name;
  }

  public String getDataType(){
    return dataType;
  }

  public int getLength(){
    return length;
  }
}
