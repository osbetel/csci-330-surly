import java.util.*;
import java.io.*;
import java.lang.*;

public class DestroyParser{

  String input;

  public DestroyParser(String input){
    this.inpput=input;
  }

  public String parseRelationName(){
    String[] line=input.split(" ");
    return line[1];
  }
}
