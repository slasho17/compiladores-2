/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

package AST;

import Lexer.*;

public class ExprUnary extends Expr {

  //atrubutos:
  private Symbol op;
  private Expr e;

  // metodos:
  public ExprUnary(Symbol op, Expr e){
    this.op = op;
    this.e = e;
  }

  public void genC(){
    if (this.op != null) {
      System.out.print(this.op.toString());
      e.genC();
    }
  }

  public Type getType() {
    return e.getType();
  }

  public Expr getExpr() {
    return this.e;
  }

}
