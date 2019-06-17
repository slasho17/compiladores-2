/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/
/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

package AST;

import java.io.*;
import Lexer.*;

public class VarDecStat extends Statement{

    //atributos:
    private Variable varDecStat;

    //metodos:
    public VarDecStat(Variable v){
        this.varDecStat = v;
    }

    //genc
    public void genC(){
        System.out.print(varDecStat.getType().getTypeName());
        System.out.print(" ");
        System.out.print(varDecStat.getId() + ";");
        System.out.println();
    }
}