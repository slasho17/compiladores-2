/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

package AST;

import java.io.*;
import java.util.*;

public class StatementList {

  //atributos:
  private ArrayList<Statement> v;
  public Boolean hasReturn;

  public StatementList(ArrayList<Statement> v) {
      this.hasReturn=false;
      this.v = v;
  }
  public void setHasReturn(){
    this.hasReturn = true;
  }

  public void genC(){

    for( Statement s : this.v){
      System.out.println();
      s.genC();
      System.out.println();
    }
  }
}
