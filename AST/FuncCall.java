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
  private String name;
  private Type type;

  //metodos:
  public FuncCall(String nome, ArrayList<Expr> lista){
    this.exprList = lista;
    this.name = nome;
  }

  public ArrayList<Expr> getExprList(){
    return this.exprList;
  }

  public String getName(){
    return this.name;
  }

  public Type getType() {
    return this.type;
  }

  //genc
  public void genC(){
    System.out.print(this.name + "(");
    
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
