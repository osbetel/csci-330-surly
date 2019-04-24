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

  public int parseAttributeCount(){
    if(input.indexOf("(")>0 && input.indexOf(")")>0){
      String sub= input.substring(input.indexOf("(")+1, input.indexOf(")"));
      String[] attri= sub.split(",");
      int num= attri.length;
      if(syntaxCheck(attri)){
        return num;
      }
      else{
        return -1;
      }
    }
    return -1;
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
