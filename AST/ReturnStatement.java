/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

package AST;

import java.io.*;

public class ReturnStatement extends Statement{

  //atributos:
  private Expr returnExpr;

  //metodos:
  public ReturnStatement( Expr expr){
    this.returnExpr = expr;
  }

  public Expr getReturnExpr(){
    return this.returnExpr;
  }

  //genc
  public void genC(){
    System.out.print( "return "); 
    this.returnExpr.genC(); 
    System.out.println(";");
  }
}
