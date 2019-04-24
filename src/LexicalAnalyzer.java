import java.util.*;
import java.io.*;
import java.lang.*;
public class LexicalAnalyzer{
  public void run(String fileName) throws Exception{
    File file = fileCheck(fileName);
    Scanner scan = new Scanner(file);
    String passIn="";
    while(scan.hasNextLine()){
      String line = scan.nextLine();
      if(line.contains("#")){
        passIn="";
      }
      else if(line.indexOf(';')!=-1){
        passIn=passIn+line;
        PassToParse(passIn);
        passIn="";
      }
      else{
        passIn=passIn+line;
      }
    }
  }
    public void PassToParse(String line){
      int action=0;
      action=actionCheck(line);

      if(action==1){
        RelationParser parse= new RelationParser(line);
        System.out.println("Creating "+parse.parseRelationName()+" with "+parse.parseAttributeCount()+" attributes." );
      }
      else if(action==2){
        InsertParser parse= new InsertParser(line);
        System.out.println("Inserting "+parse.parseInsertCount()+" attributes to "+parse.parseRelationName()+"." );
      }
      else if(action==3){
        PrintParser parse= new PrintParser(line);
        String[] relations=parse.parseRelationNames();
        System.out.println("Printing "+relations.length+" relations: ");
        for(String relation:relations){
          System.out.print(relation+" ");
        }
      }
      else{
        System.out.println("-1");
      }
    }

    public int actionCheck(String line){
      String[] arr=line.split(" ");
      if(arr[0].equals("RELATION")){
        return 1;
      }
      else if(arr[0].equals("INSERT")){
        return 2;
      }
      else if(arr[0].equals("PRINT")){
        return 3;
      }
      else{
        return 0;
      }
    }

    public File fileCheck(String fileName){
      boolean doesntExist= true;
      Scanner reader= new Scanner(System.in);
      File tfile= null;
      while(doesntExist){
        try{
          tfile=new File(fileName);
          if(tfile.exists()){
            doesntExist=false;
          }
          else{
            throw new Exception("File not found");
          }
        }
        catch(Exception e){
          System.out.println(e.getMessage()+". Retype in file name : ");
          fileName=reader.nextLine();
        }
      }
      return tfile;
    }

}
