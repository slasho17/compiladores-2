/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

package AST;

public class Write extends Statement {

    private Expr e;
    public Write(Expr e){
      this.e = e;
    }

    public void genC();
}
