/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

package AST;

import Lexer.*;

public class ExprLiteralBoolean extends Expr {

  //atributos:
  private String value;

  public ExprLiteralBoolean(boolean par){
    if( par == true){
      this.value = "1";
    }else{
      this.value = "0";
    }
  }

  public String getBooleanValue(){
    return this.value;
  }

  public Type getType() {
    return new BooleanType();
  }

  //genc
  public void genC(){
    System.out.print(getBooleanValue());
  }

}
