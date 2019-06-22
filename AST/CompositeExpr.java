/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

package AST;

import Lexer.*;

public class CompositeExpr extends Expr {

  // felipe ta usando essa classe inves de usar AndExpr to so seguindo...
  // nao só disso, mas de exprRel, exprAdd , exprMult tbm...

    //privados:
  private Expr left, right;
  private Symbol oper;

    public CompositeExpr( Expr pleft, Symbol poper, Expr pright ) {
        left = pleft;
        oper = poper;
        right = pright;
    }

    public Type getType() {
      if (oper == Symbol.EQ || oper == Symbol.NEQ || oper == Symbol.LE || oper == Symbol.LT
          || oper == Symbol.GE || oper == Symbol.GT || oper == Symbol.AND || oper == Symbol.OR) {
          return new BooleanType();
      } else {
          return new IntegerType();
      }
    }

    public void genC() {
        System.out.print("(");
        left.genC();
        System.out.print(" " + oper.toString() + " ");
        right.genC();
        System.out.print(")");
    }


}
