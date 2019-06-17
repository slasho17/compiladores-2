/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

package AST;
import java.io.*;

public class BooleanType extends Type {
	public BooleanType(){
		super("Boolean");
	}
	public String getCname() { return "int"; }

}
