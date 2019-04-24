import java.util.*;
import java.io.*;
import java.lang.*;

publib class Relation{

  String name;
  LinkedList<Attribute> schema;
  LinkedList<Tuple> tuples;

  public Relation(String name, LinkedList schema, LinkedList tuples){
    this.name = name;
    this.schema = schema;
    this.tuples = tuples;
  }

  public void print(){

  }

  public void insert(Tuple tuple){

  }

  public void delete(){

  }
}
