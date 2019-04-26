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

  public Tuple parseTuple(){
    char[] inputCharArray= input.toCharArray();

    //To do parse input into array with single qoute proccessing
    int start=0;
    int end=0;
    int roughIndex=0;
    String[] roughValues=new String[parseInsertCount()];
    for(end=0; end<=inputCharArray.length;){
      if(inputCharArray[start]!=' '){
        while(end<inputCharArray.length && inputCharArray[end]!=' '){
          if(inputCharArray[end]=='\''){
            end++;
            while(inputCharArray!='\''){
              end++;
            }
          }
          end++;
        }
        roughValues[roughIndex]=input.substring(start,end);
        roughIndex++;
        start=end;
      }
      end++;
      start++;
    }
    String[] clean = Arrays.copyOfRange(rough,2,roughValues.length);
    LinkedList attrValue= new LinkedList();

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
    return count;
  }
}
