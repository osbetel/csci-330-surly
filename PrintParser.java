import java.util.*;
 public class PrintParser{

 String input;

 PrintParser(String line){
 input=line;
 }

 public String[] parseRelationNames(){
   String[] inputArray= input.split(" ");
   String[] printArray= Arrays.copyOfRange(inputArray,1, inputArray.length);
   return printArray;
 }
}
