/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

package AST;

public class AssignExprStatement extends Statement {

    //atributos:
    private Expr right;
    private Expr left;

    public AssignExprStatement( Expr left, Expr right ) {
        this.left = left;
        this.right = right;
    }

    public void genC() {
      this.left.genC();

      if (this.right != null) {
        System.out.print(" = ");
        this.right.genC();
      }
      
      System.out.println(';');
    }
  }
