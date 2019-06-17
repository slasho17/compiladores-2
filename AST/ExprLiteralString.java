/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

package AST;

import Lexer.*;

public class ExprLiteralString extends Expr {

  //atributos:
  private String value;

  public ExprLiteralString(String par){
    this.value = par;
  }

  public String getStringValue(){
    return this.value;
  }

  public void genC(){
    System.out.print(getStringValue());
  }

}
