import java.util.*;
import java.lang.*;

public class SelectParser{
  public Relation temp;

  public SelectParser(Relation copy){
    temp= copy;
  }

  public Relation createTemp(){
    Random rand = new Random();
    LinkedList<Tuple> values = temp.getTuples();
    for(Tuple tuple: values){
      int num= rand.nextInt(0,11);
      if(num%2==0){
        values.remove(tuple);
      }
    }
    return temp;
  }
}
