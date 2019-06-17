/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

package AST;

import Lexer.*;

public class ExprLiteralInt extends Expr {

  //atributos:
  private String value;

  public ExprLiteralInt(int value){
    this.value = Integer.toString(value);
  }

  public String getIntValue(){
    return this.value;
  }

  public void genC(){
    System.out.print(getIntValue());
  }

}
