/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

package AST;

import Lexer.*;
import java.io.*;

public class ExprIdentifier extends Expr {

  //atributos:
  private String name;

  //metodos:
  public ExprIdentifier(String nome){
    this.name = nome;
  }


  public String getName(){
    return this.name;
  }

  //genC()
  public void genC() {
    System.out.print(this.name);
  }
}
