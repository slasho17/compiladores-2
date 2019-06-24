/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

package AST;

import Lexer.*;
import java.io.*;
import java.util.*;

public class FuncCall extends Expr {

  //atributos:
  private ArrayList<Expr> exprList;
  private Function func;
  private Type type;

  //metodos:
  public FuncCall(Function func, ArrayList<Expr> lista){
    this.exprList = lista;
    this.func = func;
    this.type = func.getType();
  }

  public ArrayList<Expr> getExprList(){
    return this.exprList;
  }

  public Function getFunc(){
    return this.func;
  }

  public Type getType() {
    return this.type;
  }

  public String getExprName() {
    return "FuncCall";
  }

  //genc
  public void genC(){
    System.out.print(this.func.getId() + "(");
    
    int length = this.exprList.size();
    
    if(length != 0){
      for(int i = 0; i< length; i++){
        exprList.get(i).genC();
        if(i != length - 1){
          System.out.print(",");
        }
      }
    }
  }


}
