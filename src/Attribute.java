import java.lang.*;

public class Attribute{

  private String name;
  private String dataType;
  private int length;

  public Attribute(String name, String dataType, int length) {
      this.name = name;
      this.dataType = dataType;
      this.length = length;
  }
  
  public String getName() {
      return name;
  }

  public void setName(String name) {
      this.name = name;
  }

  public String getDataType() {
    return dataType;
  }

  public int getLength() {
    return length;
  }

  public Attribute copy() {
      return new Attribute(name, dataType, length);
  }

  public String toString() {
      return name + " " + dataType + " " + length;
  }
}
