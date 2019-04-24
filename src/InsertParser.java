public class InsertParser{

  String input;

  InsertParser(String input){
    this.input=input;

  }

  public String parseRelationName(){
    String[] inputArray= input.split(" ");
    String relation=inputArray[1];
    return relation;
  }

  public int parseInsertCount(){
    char[] charArr= input.toCharArray();
    int count=0;
      for(int iter=0; iter<=charArr.length;){
        if(charArr[iter]!=' '){
           while(iter<charArr.length && charArr[iter]!=' '){
              if(charArr[iter]=='\''){
                 iter++;
                 while(charArr[iter]!='\''){
                    iter++;
                 }
              }
              iter++;
           }
           count++;
           iter++;
        }
      }
    return count - 2;
  }
  
  public Tuple parseTuple(){
      
  }
}
