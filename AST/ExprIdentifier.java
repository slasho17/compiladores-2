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
  private Type type;

  //metodos:
  public ExprIdentifier(String nome, Type type){
    this.name = nome;
    this.type = type;
  }
  
  public String getName(){
    return this.name;
  }

  public Type getType() {
    return this.type;
  }

  public String getExprName() {
    return "ExprIdentifier";
  }

  //genC()
  public void genC() {
    System.out.print(this.name);
  }
}
