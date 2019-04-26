import java.util.*;
import java.io.*;
import java.lang.*;

public class DeleteParser{

  String input;

  public DeleteParser(String input){
    this.input=input;
  }

  public String parseRelationName(){
    String[] line=input.split(" ");
    return line[1];
  }
}
