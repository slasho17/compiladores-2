/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

package AST;

import java.io.*;

public class IfStatement extends Statement{

  //atributos:
  private Expr expr;
  private StatementList thenPart;
  private StatementList elsePart;

  //metodos:
  public IfStatement( Expr expr, StatementList then, StatementList elsePart){
    this.thenPart = then;
    this.elsePart = elsePart;
    this.expr = expr;
  }

  public Expr getIfExpr(){
    return this.expr;
  }

  public StatementList getThenPart(){
    return this.thenPart;
  }

  public StatementList getElsePart(){
    return this.elsePart;
  }

  public void genC() {
    System.out.print("if(");
    this.expr.genC();
    System.out.print(") ");

    System.out.println("{");
    this.thenPart.genC();
    System.out.println("}");

    if(this.elsePart != null){
      System.out.println("else");
      System.out.println("{");
      this.elsePart.genC();
      System.out.println("}");
    }
  }
}
